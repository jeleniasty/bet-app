package com.jeleniasty.betapp.features.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final BetappUserRepository betappUserRepository;

  @Transactional
  @Override
  public UserPrincipal loadUserByUsername(String email)
    throws UsernameNotFoundException {
    return UserPrincipal.create(
      betappUserRepository
        .findByEmail(email)
        .orElseThrow(() ->
          new UsernameNotFoundException("User " + email + " not found.")
        )
    );
  }
}
