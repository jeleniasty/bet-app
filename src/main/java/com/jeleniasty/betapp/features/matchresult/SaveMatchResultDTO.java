package com.jeleniasty.betapp.features.matchresult;

public record SaveMatchResultDTO(
  Integer homeTeamScore,
  Integer awayTeamScore,
  Integer duration
) {}
