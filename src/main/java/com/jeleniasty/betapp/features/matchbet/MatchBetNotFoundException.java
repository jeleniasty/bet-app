package com.jeleniasty.betapp.features.matchbet;

public class MatchBetNotFoundException extends RuntimeException {

  public MatchBetNotFoundException(long matchBetId) {
    super("Match bet with id: " + matchBetId + " not found.");
  }
}
