package com.jeleniasty.betapp.features.matchbet.repository;

import com.jeleniasty.betapp.features.matchbet.repository.entity.MatchBet;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MatchBetRepository extends JpaRepository<MatchBet, Long> {
  @Modifying
  @Transactional
  @Query(
    "UPDATE MatchBet m " +
    "SET m.homeTeamScore = :homeTeamScore, " +
    "m.awayTeamScore = :awayTeamScore, " +
    "m.updated = :updated " +
    "WHERE m.id = :matchBetId"
  )
  void updateMatchBetById(
    @Param("homeTeamScore") Integer homeTeamScore,
    @Param("awayTeamScore") Integer awayTeamScore,
    @Param("updated") LocalDateTime updated,
    @Param("matchBetId") Long matchBetId
  );
}
