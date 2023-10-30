package com.jeleniasty.betapp.security.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {}
