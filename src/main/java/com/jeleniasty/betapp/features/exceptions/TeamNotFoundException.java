package com.jeleniasty.betapp.features.exceptions;

public class TeamNotFoundException extends RuntimeException {

  public TeamNotFoundException(String teamName) {
    super("Team '" + teamName + "' not found.");
  }
}
