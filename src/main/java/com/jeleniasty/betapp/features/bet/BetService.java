package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.exceptions.PastMatchBetException;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.user.BetappUserService;
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
  public void createBet(CreateBetDTO createBetDTO) {
    var matchToBet = matchService.fetchMatch(createBetDTO.matchId());
    if (
      matchToBet.getDate().isBefore(LocalDateTime.now())
    ) throw new PastMatchBetException(createBetDTO.matchId());

    var betResult = resultService.saveResult(createBetDTO.matchResultDTO());
    var currentUser = betappUserService.fetchUser(
      betappUserService.getCurrentUser().getId()
    );

    var newBet = new Bet(betResult, createBetDTO.betType());
    newBet.assignMatch(matchToBet);
    newBet.assignPlayer(currentUser);
  }

  @Transactional
  public void assignPoints(Long matchId) {
    //getting match result
    var matchResult = matchService.fetchMatch(matchId).getResult();

    //getting bets on that match
    var bets = betRepository.findAllByMatchId(matchId);

    //find winning bets
    assignPointsForFullTimeResultBets(
      findWinningFullTimeResultBets(bets, matchResult.getWinner())
    );

    assignPointsForCorrectScoreBets(
      findWinningCorrectScoreBets(bets, matchResult)
    );
  }

  private void assignPointsForFullTimeResultBets(List<Bet> fullTimeResultBets) {
    var playersWithAssignedPoints = fullTimeResultBets
      .stream()
      .map(Bet::getPlayer)
      .map(betappUser -> {
        betappUser.setPoints(betappUser.getPoints() + FULL_TIME_RESULT_POINTS);
        return betappUser;
      })
      .toList();

    //todo calculation of points based on odds
    betappUserService.savePlayers(playersWithAssignedPoints);
  }

  private void assignPointsForCorrectScoreBets(List<Bet> correctScoreBets) {
    var playersWithAssignedPoints = correctScoreBets
      .stream()
      .map(Bet::getPlayer)
      .map(betappUser -> {
        betappUser.setPoints(betappUser.getPoints() + CORRECT_SCORE_POINTS);
        return betappUser;
      })
      .toList();

    //todo calculation of points based on odds
    betappUserService.savePlayers(playersWithAssignedPoints);
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
      case REGULAR_TIME -> matchResult
        .getRegularTimeScore()
        .equals(Hibernate.unproxy(betResult.getRegularTimeScore()));
      case EXTRA_TIME -> matchResult
        .getExtraTimeScore()
        .equals(Hibernate.unproxy(betResult.getExtraTimeScore()));
      case PENALTIES -> matchResult
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
