package com.jeleniasty.betapp.features.exceptions;

public class CompetitionNotFoundException extends RuntimeException {

  public CompetitionNotFoundException(String code, int season) {
    super(
      "Competition with code '" +
      code +
      "' and season '" +
      season +
      "' not available"
    );
  }
}
