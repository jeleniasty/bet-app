package com.jeleniasty.betapp.features.role;

public class RoleNotFoundException extends RuntimeException {

  public RoleNotFoundException(RoleName roleName) {
    super("Role with name '" + roleName + "' not found.");
  }
}
