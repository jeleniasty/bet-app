package com.jeleniasty.betapp.features.bet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("bet")
public class BetController {

  private final BetService betService;

  @PostMapping
  public ResponseEntity<Void> createBet(
    @Valid @RequestBody CreateBetDTO createBetDTO
  ) {
    betService.createBet(createBetDTO);
    return ResponseEntity.status(201).build();
  }
}
