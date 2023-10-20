package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.MatchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("match")
public class MatchController {

  private final MatchService matchService;

  @PostMapping
  public ResponseEntity<Void> createMatch(@RequestBody SaveMatchDTO matchDTO) {
    matchService.saveMatch(matchDTO);
    return ResponseEntity.status(201).build();
  }

  @PatchMapping
  public ResponseEntity<Void> updateMatchResult(
    @RequestBody MatchResultDTO matchResultDTO
  ) {
    matchService.setMatchResult(matchResultDTO);
    return ResponseEntity.status(204).build();
  }
}
