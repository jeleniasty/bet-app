package com.jeleniasty.betapp.features.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetappUserService {

  private final BetappUserRepository betappUserRepository;

  public void savePlayers(List<BetappUser> players) {
    betappUserRepository.saveAll(players);
  }

  public void savePlayer(BetappUser player) {
    betappUserRepository.save(player);
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
}
