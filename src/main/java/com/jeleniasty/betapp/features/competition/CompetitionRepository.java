package com.jeleniasty.betapp.features.competition;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository
  extends JpaRepository<Competition, Long> {
  Optional<Competition> findCompetitionByCodeAndSeason(String code, int season);
}
