package com.jeleniasty.betapp.features.match.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchResultRepository
  extends JpaRepository<MatchResult, Long> {}