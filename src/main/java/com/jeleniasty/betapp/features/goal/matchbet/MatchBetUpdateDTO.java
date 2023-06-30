package com.jeleniasty.betapp.features.goal.matchbet;

public record MatchBetUpdateDTO(
  Integer homeTeamScore,
  Integer awayTeamScore,
  Long matchBetId
) {}
