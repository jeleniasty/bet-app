package com.jeleniasty.betapp.features.matchbet;

public record MatchBetUpdateDTO(
  Long matchBetId,
  Integer homeTeamScore,
  Integer awayTeamScore
) {}
