package com.jeleniasty.betapp.features.result.score;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
  Optional<Score> findByHomeAndAway(Integer home, Integer away);
}
