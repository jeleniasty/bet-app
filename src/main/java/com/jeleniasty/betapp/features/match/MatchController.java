package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.matchresult.SaveMatchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    matchService.createMatch(matchDTO);
    return ResponseEntity.status(201).build();
  }

  @PostMapping("/result")
  public ResponseEntity<Void> addMatchResult(
    @RequestBody SaveMatchResultDTO matchResultDTO
  ) {
    matchService.addMatchResultDTO(matchResultDTO);
    return ResponseEntity.status(201).build();
  }
}
