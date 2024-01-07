package com.jeleniasty.betapp.features.exceptions;

public class MatchNotFoundException extends RuntimeException {

  private MatchNotFoundException(String message) {
    super(message);
  }

  public static MatchNotFoundException withId(Long matchId) {
    return new MatchNotFoundException(
      "Match with id '" + matchId + "' not found"
    );
  }

  public static MatchNotFoundException withExternalId(Long externalId) {
    return new MatchNotFoundException(
      "Match with external id '" + externalId + "' not found"
    );
  }
}
