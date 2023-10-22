package com.jeleniasty.betapp.security.auth;

import lombok.Builder;

@Builder
record AuthenticationResponse(String token) {}
