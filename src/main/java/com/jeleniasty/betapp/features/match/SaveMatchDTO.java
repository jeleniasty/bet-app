package com.jeleniasty.betapp.features.match;

import java.time.LocalDateTime;

public record SaveMatchDTO(
  MatchStatus status,
  CompetitionStage stage,
  char group,
  float homeOdds,
  float awayOdds,
  LocalDateTime utcDate,
  long competitionId,
  long homeTeamId,
  long awayTeamId
) {}
