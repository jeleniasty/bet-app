package com.jeleniasty.betapp.features.exceptions;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CustomError {

  private final int status;
  private final String message;
  private final String timestamp;

  public CustomError(int status, String message, LocalDateTime timestamp) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp.toString();
  }
}
