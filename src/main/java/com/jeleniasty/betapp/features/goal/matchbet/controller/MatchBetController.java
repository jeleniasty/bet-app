package com.jeleniasty.betapp.features.goal.matchbet.controller;

import com.jeleniasty.betapp.features.goal.matchbet.MatchBetDTO;
import com.jeleniasty.betapp.features.goal.matchbet.MatchBetUpdateDTO;
import com.jeleniasty.betapp.features.goal.matchbet.repository.entity.MatchBet;
import com.jeleniasty.betapp.features.goal.matchbet.service.MatchBetService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchBetController {

  private final MatchBetService matchBetService;

  @PostMapping("matchbet")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void saveMatchBet(@RequestBody MatchBetDTO matchBet) {
    matchBetService.saveMatchBet(matchBet);
  }

  @PatchMapping("matchbet")
  @ResponseStatus(value = HttpStatus.OK)
  public void updateMatchBet(@RequestBody MatchBetUpdateDTO matchBetUpdateDTO) {
    matchBetService.updateMatchBet(matchBetUpdateDTO);
  }

  @DeleteMapping("matchbet/{id}")
  public void deleteMatch(@PathVariable Long id) {
    matchBetService.deleteMatchBet(id);
  }

  @GetMapping("matchbet/{id}")
  public Optional<MatchBet> getMatch(@PathVariable Long id) {
    return matchBetService.getMatchBet(id);
  }

  @GetMapping("matchbets")
  public Iterable<MatchBet> getMatches() {
    return matchBetService.getMatches();
  }
}
