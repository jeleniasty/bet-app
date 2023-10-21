package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.user.service.BetappUserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
  public void createBet(SaveBetDTO saveBetDTO) {
    var matchToBet = matchService.fetchMatch(saveBetDTO.matchId());
    var betResult = resultService.saveResult(saveBetDTO.saveResultDTO());
    var currentUser = betappUserService.fetchUser(
      betappUserService.getCurrentUser().getId()
    );

    var newBet = new Bet(betResult, saveBetDTO.betType());
    newBet.assignMatch(matchToBet);
    newBet.assignPlayer(currentUser);
  }

  void assignPoints(Long matchId) {
    //getting match result
    var matchResult = matchService.fetchMatch(matchId).getResult();

    //getting bets on that match
    var bets = betRepository.findAllByMatchId(matchId);

    //find winning bets
    var winningFullTimeResultBets = findWinningFullTimeResultBets(
      bets,
      matchResult.getWinner()
    );
    var winningCorrectScoreBets = findWinningCorrectScoreBets(
      bets,
      matchResult
    );
    //todo add logic to points assignment
  }

  private List<Bet> findWinningFullTimeResultBets(
    List<Bet> bets,
    Winner matchWinner
  ) {
    return bets
      .stream()
      .filter(bet -> bet.getResult().getWinner() == matchWinner)
      .toList();
  }

  private List<Bet> findWinningCorrectScoreBets(
    List<Bet> bets,
    Result matchResult
  ) {
    return bets
      .stream()
      .filter(bet -> checkExactScore(bet.getResult(), matchResult))
      .toList();
  }

  private boolean checkExactScore(Result betResult, Result matchResult) {
    return switch (matchResult.getDuration()) {
      case REGULAR_TIME -> matchResult
        .getRegularTimeScore()
        .equals(betResult.getRegularTimeScore());
      case EXTRA_TIME -> matchResult
        .getExtraTimeScore()
        .equals(betResult.getExtraTimeScore());
      case PENALTIES -> matchResult
        .getPenaltiesScore()
        .equals(betResult.getPenaltiesScore());
      //todo add proper equals to Score entity object
    };
  }
}
