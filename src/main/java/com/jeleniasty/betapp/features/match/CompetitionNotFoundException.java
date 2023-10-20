package com.jeleniasty.betapp.features.match;

public class CompetitionNotFoundException extends RuntimeException {

  public CompetitionNotFoundException(long competitionId) {
    super("Competition with '" + competitionId + "' not found");
  }
}
