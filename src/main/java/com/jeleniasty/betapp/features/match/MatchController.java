package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.MatchResultDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;

  @PostMapping("/match")
  public ResponseEntity<Void> createMatch(@RequestBody SaveMatchDTO matchDTO) {
    matchService.saveMatch(matchDTO);
    return ResponseEntity.status(201).build();
  }

  @PatchMapping("match")
  public ResponseEntity<Void> updateMatchResult(
    @RequestBody MatchResultDTO matchResultDTO
  ) {
    matchService.setMatchResult(matchResultDTO);
    return ResponseEntity.status(204).build();
  }

  @GetMapping("/match/{id}")
  public ResponseEntity<MatchDTO> getUpcomingMatch(@PathVariable Long id) {
    return ResponseEntity.ok(matchService.getUpcomingMatch(id));
  }

  @GetMapping("matches/upcoming")
  public ResponseEntity<List<UpcomingMatchDTO>> getUpcomingMatches() {
    return ResponseEntity.ok(matchService.getUpcomingMatches());
  }
}
