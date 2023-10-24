package com.jeleniasty.betapp.features.exceptions;

public class PastMatchBetException extends RuntimeException {

  public PastMatchBetException(Long matchId) {
    super(
      "Match with id '" +
      matchId +
      "' is finished. Matches from the past cannot be bet on."
    );
  }
}
