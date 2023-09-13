package com.jeleniasty.betapp.features.matchbet;

public record MatchBetDTO(
  Integer homeTeamScore,
  Integer awayTeamScore,
  Long matchId
) {}
