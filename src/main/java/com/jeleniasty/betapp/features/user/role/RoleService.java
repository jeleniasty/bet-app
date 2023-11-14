package com.jeleniasty.betapp.features.user.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;

  public Role findRoleByName(RoleName roleName) {
    return roleRepository
      .findByName(roleName)
      .orElseThrow(() -> new RoleNotFoundException(roleName));
  }
}
