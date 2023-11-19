package com.jeleniasty.betapp.features.competition;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompetitionController {

  private final CompetitionService competitionService;

  @PostMapping("/competition")
  public ResponseEntity<Void> createNewCompetition(
    @RequestBody CreateCompetitonRequest createCompetitonRequest
  ) {
    this.competitionService.createNewCompetition(createCompetitonRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
