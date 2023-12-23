package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.dto.UpcomingMatchDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;

  @GetMapping("/match/{id}")
  public ResponseEntity<MatchDTO> getMatch(@PathVariable Long id) {
    return ResponseEntity.ok(matchService.getMatch(id));
  }

  @GetMapping("/matches/upcoming")
  public ResponseEntity<List<UpcomingMatchDTO>> getUpcomingMatches() {
    return ResponseEntity.ok(matchService.getUpcomingMatches());
  }
}
