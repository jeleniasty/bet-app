package com.jeleniasty.betapp.features.competition;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompetitionController {

  private final CompetitionService competitionService;

  @PostMapping("/competition")
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  public ResponseEntity<CompetitionDTO> createNewCompetition(
    @RequestBody CreateCompetitonRequest createCompetitonRequest
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        this.competitionService.createNewCompetition(createCompetitonRequest)
      );
  }
}
