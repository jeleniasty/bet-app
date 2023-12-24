package com.jeleniasty.betapp.features.bet;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BetController {

  private final BetService betService;

  @PostMapping("/bet")
  public ResponseEntity<BetDTO> createBet(
    @Valid @RequestBody CreateBetDTO createBetDTO
  ) {
    return ResponseEntity.status(201).body(betService.createBet(createBetDTO));
  }

  @GetMapping("/bets/user/{matchId}")
  public ResponseEntity<List<BetDTO>> getCurrentUserBets(
    @PathVariable Long matchId
  ) {
    return ResponseEntity.ok(betService.getCurrentUserBets(matchId));
  }
}
