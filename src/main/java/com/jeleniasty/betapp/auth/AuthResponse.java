package com.jeleniasty.betapp.auth;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {}
