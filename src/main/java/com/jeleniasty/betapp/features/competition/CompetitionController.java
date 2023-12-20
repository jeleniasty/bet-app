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
  public ResponseEntity<CompetitionDTO> createNewCompetition(
    @RequestBody CreateCompetitonRequest createCompetitonRequest
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        this.competitionService.createNewCompetition(createCompetitonRequest)
      );
  }
  //TODO add authorization only for admin
}
