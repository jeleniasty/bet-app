package com.jeleniasty.betapp.features.exceptions;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalValidationHandler extends ResponseEntityExceptionHandler {

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

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<CustomError> handleGenericError(
    RuntimeException runtimeException
  ) {
    return createCustomError(
      HttpStatus.INTERNAL_SERVER_ERROR,
      runtimeException
    );
  }

  @ExceptionHandler(AuthenticationException.class)
  protected ResponseEntity<CustomError> handleAuthenticationExceptions(
    AuthenticationException authenticationException
  ) {
    return createCustomError(HttpStatus.UNAUTHORIZED, authenticationException);
  }

  @ExceptionHandler(PastMatchBetException.class)
  protected ResponseEntity<CustomError> handlePastMatchBetException(
    PastMatchBetException pastMatchBetException
  ) {
    return createCustomError(HttpStatus.BAD_REQUEST, pastMatchBetException);
  }

  private ResponseEntity<CustomError> createCustomError(
    HttpStatus httpStatus,
    Exception exception
  ) {
    return new ResponseEntity<>(
      new CustomError(
        httpStatus.value(),
        exception.getMessage(),
        LocalDateTime.now()
      ),
      httpStatus
    );
  }
}