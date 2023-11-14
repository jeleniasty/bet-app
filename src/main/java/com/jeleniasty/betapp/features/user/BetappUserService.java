package com.jeleniasty.betapp.features.user;

import com.jeleniasty.betapp.features.user.dto.UserScoreDTO;
import com.jeleniasty.betapp.features.user.role.RoleName;
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

  public UserPrincipal getCurrentUser() {
    return (UserPrincipal) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();
  }

  public BetappUser fetchUser(long userId) {
    return betappUserRepository
      .findById(userId)
      .orElseThrow(() -> new UserNotFoundException(userId));
  }

  public List<UserScoreDTO> getPlayerScores() {
    var players = this.betappUserRepository.findByRolesName(RoleName.PLAYER);
    return players
      .stream()
      .map(player ->
        new UserScoreDTO(
          player.getId(),
          player.getUsername(),
          player.getPoints()
        )
      )
      .toList();
  }
}
