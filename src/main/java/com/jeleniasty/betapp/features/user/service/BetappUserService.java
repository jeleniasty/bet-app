package com.jeleniasty.betapp.features.user.service;

import com.jeleniasty.betapp.features.user.repository.BetappUserRepository;
import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetappUserService {

  private final BetappUserRepository betappUserRepository;

  public BetappUser getCurrentUser() {
    return (BetappUser) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();
  }

  public UserDetailsService getUserDetailsService() {
    return username ->
            betappUserRepository
                    .findByEmail(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User " + username + " not found.")
                    );
  }
}
