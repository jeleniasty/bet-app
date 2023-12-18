package com.jeleniasty.betapp.features.exceptions;

public class RequestLimitExceededException extends RuntimeException {

  public RequestLimitExceededException(String message) {
    super("Requests rate limit exceeded for url: " + message);
  }
}
