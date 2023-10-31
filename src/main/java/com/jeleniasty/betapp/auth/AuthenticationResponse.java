package com.jeleniasty.betapp.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {}
