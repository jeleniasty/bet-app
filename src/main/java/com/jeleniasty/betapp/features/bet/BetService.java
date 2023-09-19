package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.MatchNotFoundException;
import com.jeleniasty.betapp.features.match.MatchRepository;
import com.jeleniasty.betapp.features.match.result.MatchResult;
import com.jeleniasty.betapp.features.user.service.BetappUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BetService {

  private final BetRepository betRepository;
  private final BetappUserService betappUserService;
  private final MatchRepository matchRepository;

  @Transactional
  public void createBet(SaveBetDTO saveBetDTO) {
    var betToSave = new Bet(
      new MatchResult(saveBetDTO.homeTeamScore(), saveBetDTO.awayTeamScore())
    );

    var currentUser = betappUserService.getCurrentUser();
    var match = matchRepository
      .findById(saveBetDTO.matchId())
      .orElseThrow(() -> new MatchNotFoundException(saveBetDTO.matchId()));
    betToSave.setBetPlayer(currentUser);
    betToSave.setMatch(match);

    betRepository.save(betToSave);
  }
}
