package com.jeleniasty.betapp.features.user.service;

import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetappUserService {

  public BetappUser getCurrentUser() {
    return (BetappUser) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();
  }
}
