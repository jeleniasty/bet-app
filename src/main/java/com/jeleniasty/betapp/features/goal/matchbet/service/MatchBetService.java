package com.jeleniasty.betapp.features.goal.matchbet.service;

import com.jeleniasty.betapp.features.goal.matchbet.MatchBetDTO;
import com.jeleniasty.betapp.features.goal.matchbet.MatchBetUpdateDTO;
import com.jeleniasty.betapp.features.goal.matchbet.repository.MatchBetRepository;
import com.jeleniasty.betapp.features.goal.matchbet.repository.entity.MatchBet;
import com.jeleniasty.betapp.features.goal.user.service.BetappUserService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchBetService {

  private final MatchBetRepository matchBetRepository;
  private final BetappUserService userService;

  public void saveMatchBet(MatchBetDTO matchBet) {
    matchBetRepository.save(
      new MatchBet(
        matchBet.homeTeamScore(),
        matchBet.awayTeamScore(),
        LocalDateTime.now(),
        userService.getCurrentUser().getId(),
        matchBet.matchId()
      )
    );
  }

  public void deleteMatchBet(Long id) {
    matchBetRepository.deleteById(id);
  }

  public Optional<MatchBet> getMatchBet(Long id) {
    return matchBetRepository.findById(id);
  }

  public Iterable<MatchBet> getMatches() {
    return matchBetRepository.findAll();
  }

  public void updateMatchBet(MatchBetUpdateDTO matchBetUpdateDTO) {
    matchBetRepository.updateMatchBetById(
      matchBetUpdateDTO.homeTeamScore(),
      matchBetUpdateDTO.awayTeamScore(),
      LocalDateTime.now(),
      matchBetUpdateDTO.matchBetId()
    );
  }
}
