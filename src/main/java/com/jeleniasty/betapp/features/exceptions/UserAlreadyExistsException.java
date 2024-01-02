package com.jeleniasty.betapp.features.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

  private UserAlreadyExistsException(String message) {
    super(message);
  }

  public static void emailAlreadyExists(String email) {
    throw new UserAlreadyExistsException(
      "User with email '" + email + "' already exists."
    );
  }

  public static void usernameAlreadyExists(String username) {
    throw new UserAlreadyExistsException(
      "User with email '" + username + "' already exists."
    );
  }
}
