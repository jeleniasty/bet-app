package com.jeleniasty.betapp.httpclient.odds;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.util.List;

@JsonDeserialize(using = OddsResponseDeserializer.class)
public record OddsResponse(
  String id,
  String competition,
  String homeTeam,
  String awayTeam,
  Instant commenceTime,
  List<Outcome> outcomes
) {}
