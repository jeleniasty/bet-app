package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.user.service.BetappUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetService {

  private final BetappUserService betappUserService;
  private final MatchService matchService;
  private final ResultService resultService;
  private final BetRepository betRepository;

  @Transactional
  public void createBet(MatchResultDTO matchResultDTO) {
    var matchToBet = matchService.fetchMatch(matchResultDTO.matchId());
    var betResult = resultService.saveResult(matchResultDTO.saveResultDTO());
    var currentUser = betappUserService.fetchUser(
      betappUserService.getCurrentUser().getId()
    );

    var newBet = new Bet(betResult);
    newBet.assignMatch(matchToBet);
    newBet.assignPlayer(currentUser);
  }

  void assignPoints(Long matchId) {
    var matchResult = matchService.fetchMatch(matchId).getResult();

    var bets = betRepository.findAllByMatchId(matchId);
    //add logic to points assignment
  }
}
