package com.jeleniasty.betapp.features.goal.matchbet;

public record MatchBetDTO(
  Integer homeTeamScore,
  Integer awayTeamScore,
  Long matchId
) {}
