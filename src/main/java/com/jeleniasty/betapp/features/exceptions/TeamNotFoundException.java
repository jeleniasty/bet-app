package com.jeleniasty.betapp.features.exceptions;

public class TeamNotFoundException extends RuntimeException {

  public TeamNotFoundException(long teamId) {
    super("Team with id '" + teamId + "' not found.");
  }
}
