package com.jeleniasty.betapp.features.match;

import java.time.LocalDateTime;

public record SaveMatchDTO(
  String homeTeamCode,
  String awayTeamCode,
  LocalDateTime startTime,
  Long stadium_id
) {}
