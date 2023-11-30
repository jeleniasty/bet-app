package com.jeleniasty.betapp.features.odds;

import java.time.LocalDateTime;

public record MatchOddsDTO(
  String homeTeamName,
  String awayTeamName,
  LocalDateTime date,
  float homeOdds,
  float awayOdds,
  float drawOdds
) {}
