package com.jeleniasty.betapp.features.match.repository;

import com.jeleniasty.betapp.features.match.repository.entity.Match;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
  @Modifying
  @Transactional
  @Query(
    "UPDATE Match m SET m.homeTeamScore = :home_team_score,m.awayTeamScore = :away_team_score " +
    "WHERE m.id = :matchId"
  )
  void setMatchResult(
    @Param("home_team_score") Integer homeTeamScore,
    @Param("away_team_score") Integer awayTeamScore,
    @Param("matchId") Long matchId
  );
}
