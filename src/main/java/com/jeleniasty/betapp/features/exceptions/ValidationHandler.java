package com.jeleniasty.betapp.features.exceptions;

import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    @NotNull HttpHeaders headers,
    @NotNull HttpStatusCode status,
    @NotNull WebRequest request
  ) {
    Map<String, String> errors = new HashMap<>();
    ex
      .getBindingResult()
      .getAllErrors()
      .forEach(error -> {
        var fieldName = ((FieldError) error).getField();
        var message = error.getDefaultMessage();

        errors.put(fieldName, message);
      });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = { PastMatchBetException.class })
  protected ResponseEntity<Object> handlePastMatchBetException(
    PastMatchBetException ex
  ) {
    Map<String, String> errors = new HashMap<>();
    errors.put("errorMessage", ex.getMessage());
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
