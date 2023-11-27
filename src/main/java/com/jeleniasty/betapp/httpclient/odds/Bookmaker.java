package com.jeleniasty.betapp.httpclient.odds;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.Instant;
import java.util.List;

public record Bookmaker(
  @JsonAlias("title") String title,
  @JsonAlias("last_update") Instant lastUpdate,
  List<Market> markets
) {}
