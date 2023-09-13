package com.jeleniasty.betapp.features.matchbet;

import com.jeleniasty.betapp.features.matchbet.repository.entity.MatchBet;
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
