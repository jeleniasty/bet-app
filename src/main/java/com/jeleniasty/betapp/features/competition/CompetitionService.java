package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.exceptions.CompetitionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionService {

  private final CompetitionRepository competitionRepository;

  public Competition fetchCompetition(long competitionId) {
    return competitionRepository
      .findById(competitionId)
      .orElseThrow(() -> new CompetitionNotFoundException(competitionId));
  }
}
