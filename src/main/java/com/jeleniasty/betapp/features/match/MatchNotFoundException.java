package com.jeleniasty.betapp.features.match;

public class MatchNotFoundException extends RuntimeException {

  public MatchNotFoundException(Long matchId) {
    super("Match with id: " + matchId + " not found.");
  }
}
