package com.jeleniasty.betapp.httpclient.footballdata;

import com.jeleniasty.betapp.features.competition.CompetitionType;
import java.time.LocalDate;
import java.util.List;

public record CompetitionMatchesResponse(
  Filters filters,
  ResultSet resultSet,
  CompetitionResponse competition,
  List<MatchResponse> matches
) {
  public record Filters(int season) {}

  public record ResultSet(
    int count,
    LocalDate first,
    LocalDate last,
    int played
  ) {}

  public record CompetitionResponse(
    String name,
    String code,
    CompetitionType type,
    String emblem
  ) {}
}
