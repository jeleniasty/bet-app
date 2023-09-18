package com.jeleniasty.betapp.features.match.result;

public record SaveMatchResultDTO(
  Integer homeTeamScore,
  Integer awayTeamScore,
  Integer duration
) {}
