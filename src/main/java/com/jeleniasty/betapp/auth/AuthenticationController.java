package com.jeleniasty.betapp.auth;

import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<AuthenticationResponse> register(
    RegisterRequest request
  ) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping(
    value = "/api/login",
    consumes = "application/x-www-form-urlencoded"
  )
  public ResponseEntity<AuthenticationResponse> login(
    AuthenticationRequest request
  ) {
    return ResponseEntity.ok(authService.authenticate(request));
  }
}
