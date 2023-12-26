package com.jeleniasty.betapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authService;

  @PostMapping("/api/register")
  public ResponseEntity<AuthResponse> register(
    @RequestBody RegisterRequest request
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(authService.register(request));
  }

  @PostMapping("/api/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.authenticate(request));
  }
}
