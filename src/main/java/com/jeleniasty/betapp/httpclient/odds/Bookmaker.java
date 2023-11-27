package com.jeleniasty.betapp.httpclient.odds;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.util.List;

@JsonDeserialize(using = BookmakerDeserializer.class)
public record Bookmaker(
  @JsonAlias("title") String title,
  @JsonAlias("last_update") Instant lastUpdate,
  List<Outcome> outcomes
) {}
