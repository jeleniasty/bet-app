package com.jeleniasty.betapp.httpclient.odds;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.Instant;
import java.util.List;

public record OddsResponse(
  String id,
  @JsonAlias("commence_time") Instant commenceTime,
  @JsonAlias("home_team") String homeTeam,
  @JsonAlias("away_team") String awayTeam,
  List<Bookmaker> bookmakers
) {}
