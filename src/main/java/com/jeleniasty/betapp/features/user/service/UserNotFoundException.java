package com.jeleniasty.betapp.features.user.service;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(long userId) {
    super("User with id '" + userId + "' not found.");
  }
}
