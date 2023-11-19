package com.jeleniasty.betapp.features.exceptions;

public class CompetitionNotFoundException extends RuntimeException {

  public CompetitionNotFoundException(long competitionId) {
    super("Competition with '" + competitionId + "' not found");
  }

  public CompetitionNotFoundException(String code, int season) {
    super(
      "Competition with code: '" + code + "' in season " + season + " not found"
    );
  }
}
