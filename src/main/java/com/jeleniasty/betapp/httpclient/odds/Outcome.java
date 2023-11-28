package com.jeleniasty.betapp.httpclient.odds;

import java.time.Instant;

public record Outcome(
  String title,
  Instant lastUpdate,
  double homeOdds,
  double awayOdds,
  double drawOdds
) {}
