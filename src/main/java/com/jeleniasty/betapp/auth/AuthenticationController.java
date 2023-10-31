package com.jeleniasty.betapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authService;

  @PostMapping(
    value = "/api/register",
    consumes = "application/x-www-form-urlencoded"
  )
  public ResponseEntity<Void> register(RegisterRequest request) {
    authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping(
    value = "/api/login",
    consumes = "application/x-www-form-urlencoded"
  )
  public ResponseEntity<AuthResponse> login(AuthRequest request) {
    return ResponseEntity.ok(authService.authenticate(request));
  }
}
