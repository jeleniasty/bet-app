package com.jeleniasty.betapp.features.user;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(long userId) {
    super("User with id '" + userId + "' not found.");
  }
}
