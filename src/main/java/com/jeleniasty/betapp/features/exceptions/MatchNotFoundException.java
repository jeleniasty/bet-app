package com.jeleniasty.betapp.features.exceptions;

import java.time.LocalDateTime;

public class MatchNotFoundException extends RuntimeException {

  public MatchNotFoundException(Long matchId) {
    super("Match with id '" + matchId + "' not found");
  }

  public MatchNotFoundException(
    String homeTeamName,
    String awayTeamName,
    LocalDateTime dateTime
  ) {
    super(
      "Match between " +
      homeTeamName +
      " and " +
      awayTeamName +
      " of " +
      dateTime +
      " not found."
    );
  }
}
