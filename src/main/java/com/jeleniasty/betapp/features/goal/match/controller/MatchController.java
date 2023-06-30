package com.jeleniasty.betapp.features.goal.match.controller;

import com.jeleniasty.betapp.features.goal.match.repository.entity.Match;
import com.jeleniasty.betapp.features.goal.match.service.MatchService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;

  @PostMapping("match")
  public void saveMatch(@RequestBody Match match) {
    matchService.saveMatch(match);
  }

  @PatchMapping("match")
  public void saveMatchResult(@RequestBody Match match) {
    matchService.saveMatchResult(match);
  }

  @DeleteMapping("match/{id}")
  public void deleteMatch(@PathVariable Long id) {
    matchService.deleteMatch(id);
  }

  @GetMapping("match/{id}")
  public Optional<Match> getMatch(@PathVariable Long id) {
    return matchService.getMatch(id);
  }

  @GetMapping("matches")
  public Iterable<Match> getMatches() {
    return matchService.getMatches();
  }
}
