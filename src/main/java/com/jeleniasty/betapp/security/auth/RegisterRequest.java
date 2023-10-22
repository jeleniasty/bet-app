package com.jeleniasty.betapp.security.auth;

import com.jeleniasty.betapp.features.role.RoleName;
import java.util.Set;

record RegisterRequest(
  String username,
  String email,
  String password,
  Set<RoleName> roleNames
) {}
