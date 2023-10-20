package com.jeleniasty.betapp.features.user.service;

import com.jeleniasty.betapp.features.user.dto.UserScoreDTO;
import com.jeleniasty.betapp.features.user.repository.BetappUserRepository;
import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import com.jeleniasty.betapp.features.user.repository.entity.BetappUserRole;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetappUserService {

  private final BetappUserRepository betappUserRepository;

  public List<UserScoreDTO> fetchUserScores() {
    var users = betappUserRepository.findAllByBetappUserRole(
      BetappUserRole.PLAYER
    );

    return users
      .stream()
      .map(user ->
        new UserScoreDTO(user.getId(), user.getUsername(), user.getPoints())
      )
      .toList();
  }

  public BetappUser getCurrentUser() {
    return (BetappUser) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();
  }

  public BetappUser fetchUser(long userId) {
    return betappUserRepository
      .findById(userId)
      .orElseThrow(() -> new UserNotFoundException(userId));
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
