package com.jeleniasty.betapp.features.competition;

public record CompetitionDTO(
  Long id,
  String name,
  String code,
  CompetitionType type,
  Integer season
) {}
