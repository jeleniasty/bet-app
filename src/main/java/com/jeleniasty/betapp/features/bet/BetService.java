package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.exceptions.PastMatchBetException;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.user.BetappUserService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BetService {

  private final BetappUserService betappUserService;
  private final MatchService matchService;
  private final ResultService resultService;
  private final BetRepository betRepository;
  public final int FULL_TIME_RESULT_POINTS = 2;
  public final int CORRECT_SCORE_POINTS = 5;

  @Transactional
  public List<BetDTO> getCurrentUserBets(long matchId) {
    var currentUserId = betappUserService.getCurrentUser().getId();
    return betRepository
      .findAllByMatchAndAndPlayer(matchId, currentUserId)
      .stream()
      .map(bet ->
        new BetDTO(
          bet.getId(),
          bet.getBetType(),
          resultService.mapToDTO(bet.getResult()).orElse(null),
          bet.getCreatedAt()
        )
      )
      .toList();
  }

  @Transactional
  public void createBet(CreateBetDTO createBetDTO) {
    var matchToBet = matchService.findMatch(createBetDTO.matchId());
    if (
      matchToBet.getDate().isBefore(LocalDateTime.now())
    ) throw new PastMatchBetException(createBetDTO.matchId());

    var betResult = resultService.saveResult(createBetDTO.resultDTO());
    var currentUser = betappUserService.fetchUser(
      betappUserService.getCurrentUser().getId()
    );

    var newBet = new Bet(betResult, createBetDTO.betType());
    newBet.assignMatch(matchToBet);
    newBet.assignPlayer(currentUser);
  }

  @Transactional
  public void assignPoints(Long matchId) {
    var match = matchService.findMatch(matchId);

    var bets = betRepository.findAllByMatchId(matchId);

    assignPointsForFullTimeResultBets(bets, match);

    assignPointsForCorrectScoreBets(bets, match);
  }

  private void assignPointsForFullTimeResultBets(
    List<Bet> fullTimeResultBets,
    Match match
  ) {
    var playersWithAssignedPoints = findWinningFullTimeResultBets(
      fullTimeResultBets,
      match.getResult().getWinner()
    )
      .stream()
      .map(Bet::getPlayer)
      .map(betappUser -> {
        betappUser.setPoints(
          betappUser.getPoints() +
          calculatePoints(getWinnerOdds(match), FULL_TIME_RESULT_POINTS)
        );
        return betappUser;
      })
      .toList();

    betappUserService.savePlayers(playersWithAssignedPoints);
  }

  private void assignPointsForCorrectScoreBets(
    List<Bet> correctScoreBets,
    Match match
  ) {
    var playersWithAssignedPoints = findWinningCorrectScoreBets(
      correctScoreBets,
      match.getResult()
    )
      .stream()
      .map(Bet::getPlayer)
      .map(betappUser -> {
        betappUser.setPoints(
          betappUser.getPoints() +
          calculatePoints(getWinnerOdds(match), CORRECT_SCORE_POINTS)
        );
        return betappUser;
      })
      .toList();

    betappUserService.savePlayers(playersWithAssignedPoints);
  }

  private double calculatePoints(float odds, int scoreMultiplier) {
    return new BigDecimal(Float.toString(odds))
      .multiply(BigDecimal.valueOf(scoreMultiplier))
      .setScale(2, RoundingMode.HALF_UP)
      .doubleValue();
  }

  private float getWinnerOdds(Match match) {
    var matchWinner = match.getResult().getWinner();
    if (matchWinner == Winner.HOME_TEAM) {
      return match.getHomeOdds();
    } else if (matchWinner == Winner.AWAY_TEAM) {
      return match.getAwayOdds();
    } else {
      return match.getDrawOdds();
    }
  }

  private List<Bet> findWinningFullTimeResultBets(
    List<Bet> bets,
    Winner matchWinner
  ) {
    return bets
      .stream()
      .filter(this::isBetFullTimeResult)
      .filter(bet -> bet.getResult().getWinner() == matchWinner)
      .toList();
  }

  private List<Bet> findWinningCorrectScoreBets(
    List<Bet> bets,
    Result matchResult
  ) {
    return bets
      .stream()
      .filter(this::isBetCorrectScore)
      .filter(bet -> checkExactScore(bet.getResult(), matchResult))
      .toList();
  }

  private boolean checkExactScore(Result betResult, Result matchResult) {
    return switch (matchResult.getDuration()) {
      case REGULAR -> matchResult
        .getRegularTimeScore()
        .equals(Hibernate.unproxy(betResult.getRegularTimeScore()));
      case EXTRA -> matchResult
        .getExtraTimeScore()
        .equals(Hibernate.unproxy(betResult.getExtraTimeScore()));
      case PENALTY_SHOOTOUT -> matchResult
        .getPenaltiesScore()
        .equals(Hibernate.unproxy(betResult.getPenaltiesScore()));
    };
  }

  private boolean isBetFullTimeResult(Bet bet) {
    return bet.getBetType() == BetType.FULL_TIME_RESULT;
  }

  private boolean isBetCorrectScore(Bet bet) {
    return bet.getBetType() == BetType.CORRECT_SCORE;
  }
}
