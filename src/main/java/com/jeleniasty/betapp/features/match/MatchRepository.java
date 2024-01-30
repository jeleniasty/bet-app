package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.match.dto.UpcomingMatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
  @Query(
    value = "SELECT m.id, ht.name as homeTeam, ht.flag as homeFlag, at.name as awayTeam, at.flag as awayFlag , m.home_odds as homeOdds, m.away_odds as awayOdds, m.draw_odds as drawOdds, m.date as matchDate " +
    "FROM betapp.match m " +
    "JOIN betapp.team ht ON ht.id = m.home_team JOIN betapp.team at ON at.id = m.away_team " +
    "WHERE m.status='TIMED' AND m.date >= :date " +
    "ORDER BY m.date;",
    nativeQuery = true
  )
  List<UpcomingMatchDTO> findUpcomingMatches(
    @Param("date") Instant timestamp,
    Pageable pageable
  );

  Optional<Match> findByHomeTeamNameContainingAndAwayTeamNameContainingAndDateAndStatusEquals(
    String homeTeamName,
    String awayTeamName,
    LocalDateTime date,
    MatchStatus matchStatus
  );

  List<Match> findAllByDateBetween(
    LocalDateTime startDate,
    LocalDateTime endDate
  );

  Optional<Match> findByExternalId(Long externalId);
}
