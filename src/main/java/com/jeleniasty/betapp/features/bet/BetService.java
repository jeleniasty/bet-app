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

  private final BetRepository betRepository;
  private final BetappUserService betappUserService;
  private final MatchService matchService;
  private final ResultService resultService;

  @Transactional
  public void createBet(SaveBetDTO saveBetDTO) {
    var matchToBet = matchService.fetchMatch(saveBetDTO.matchId());
    var betResult = resultService.saveResult(saveBetDTO.saveResultDTO());
    var currentUser = betappUserService.fetchUser(
      betappUserService.getCurrentUser().getId()
    );

    var newBet = new Bet(betResult);
    newBet.assignMatch(matchToBet);
    newBet.assignPlayer(currentUser);

    betRepository.save(newBet);
  }
}
