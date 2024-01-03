package com.jeleniasty.betapp.features.exceptions;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
    log.error("An unexpected error occurred: ", runtimeException);
    return createCustomError(
      HttpStatus.INTERNAL_SERVER_ERROR,
      runtimeException
    );
  }

  @ExceptionHandler(AuthenticationException.class)
  protected ResponseEntity<CustomError> handleAuthenticationExceptions(
    AuthenticationException authenticationException
  ) {
    log.error("Authentication error occurred: ", authenticationException);
    return createCustomError(HttpStatus.UNAUTHORIZED, authenticationException);
  }

  @ExceptionHandler(PastMatchBetException.class)
  protected ResponseEntity<CustomError> handlePastMatchBetException(
    PastMatchBetException pastMatchBetException
  ) {
    log.error("An error occurred: ", pastMatchBetException);
    return createCustomError(HttpStatus.BAD_REQUEST, pastMatchBetException);
  }

  @ExceptionHandler(RequestLimitExceededException.class)
  protected void handleRequestLimitExceededException(
    RequestLimitExceededException requestLimitExceededException
  ) {
    log.warn(requestLimitExceededException.getMessage());
  }

  @ExceptionHandler(CompetitionNotFoundException.class)
  protected ResponseEntity<CustomError> handleCompetitionNotFoundException(
    CompetitionNotFoundException competitionNotFoundException
  ) {
    return createCustomError(
      HttpStatus.NOT_FOUND,
      competitionNotFoundException
    );
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  protected ResponseEntity<CustomError> handleUserAlreadyExistsException(
    UserAlreadyExistsException userAlreadyExistsException
  ) {
    return createCustomError(HttpStatus.CONFLICT, userAlreadyExistsException);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<CustomError> handleAccessDeniedException() {
    return createCustomError(
      HttpStatus.FORBIDDEN,
      new AccessDeniedException(
        "You have no permission to access this resource."
      )
    );
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
