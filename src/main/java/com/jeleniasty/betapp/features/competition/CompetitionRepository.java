package com.jeleniasty.betapp.features.competition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository
  extends JpaRepository<Competition, Long> {
  boolean existsByCodeAndSeason(String code, int season);
  Optional<Competition> findCompetitionByCodeAndSeason(String code, int season);

  List<Competition> findCompetitionByEndDateAfter(LocalDate date);
}
