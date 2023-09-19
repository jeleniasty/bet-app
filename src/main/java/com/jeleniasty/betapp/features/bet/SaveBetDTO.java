package com.jeleniasty.betapp.features.bet;

public record SaveBetDTO(
  Integer homeTeamScore,
  Integer awayTeamScore,
  Long matchId
) {}
