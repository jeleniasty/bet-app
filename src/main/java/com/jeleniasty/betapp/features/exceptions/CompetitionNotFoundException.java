package com.jeleniasty.betapp.features.exceptions;

public class CompetitionNotFoundException extends RuntimeException {

  public CompetitionNotFoundException(long competitionId) {
    super("Competition with '" + competitionId + "' not found");
  }
}
