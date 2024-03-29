package com.jeleniasty.betapp.auth;

import com.jeleniasty.betapp.features.user.role.RoleName;
import java.util.Set;

public record RegisterRequest(
  String username,
  String email,
  String password,
  Set<RoleName> roleNames
) {}
