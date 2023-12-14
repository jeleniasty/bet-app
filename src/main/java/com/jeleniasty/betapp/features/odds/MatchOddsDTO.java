package com.jeleniasty.betapp.features.odds;

import java.time.LocalDateTime;

public record MatchOddsDTO(
  String homeTeamName,
  String awayTeamName,
  LocalDateTime date,
  double homeOdds,
  double awayOdds,
  double drawOdds
) {}
