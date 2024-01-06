package com.jeleniasty.betapp.features.exceptions;

public class CompetitionAlreadyExiststException extends RuntimeException {

  CompetitionAlreadyExiststException(String message) {
    super(message);
  }

  public static void of(String code, int season) {
    throw new CompetitionAlreadyExiststException(
      "Competition " + code + " " + season + " already exists."
    );
  }
}
