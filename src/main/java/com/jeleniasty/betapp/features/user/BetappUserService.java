package com.jeleniasty.betapp.features.user;

import com.jeleniasty.betapp.auth.RegisterRequest;
import com.jeleniasty.betapp.features.exceptions.UserAlreadyExistsException;
import com.jeleniasty.betapp.features.user.dto.UserScoreDTO;
import com.jeleniasty.betapp.features.user.role.RoleName;
import com.jeleniasty.betapp.features.user.role.RoleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetappUserService {

  private final BetappUserRepository betappUserRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  private static final double baseUserPoints = 0.00d;

  public void savePlayers(List<BetappUser> players) {
    betappUserRepository.saveAll(players);
  }

  public BetappUser registerPlayer(RegisterRequest request) {
    validateUserEmailAndUsername(request.username(), request.email());

    var user = new BetappUser(
      request.username(),
      request.email(),
      passwordEncoder.encode(request.password()),
      baseUserPoints,
      request
        .roleNames()
        .stream()
        .map(roleService::findRoleByName)
        .collect(Collectors.toSet())
    );

    return betappUserRepository.save(user);
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

  public BetappUser fetchUser(String email) {
    return betappUserRepository
      .findByEmail(email)
      .orElseThrow(() -> new UserNotFoundException(email));
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

  private void validateUserEmailAndUsername(String username, String email) {
    if (betappUserRepository.existsByUsername(username)) {
      UserAlreadyExistsException.usernameAlreadyExists(username);
    }

    if (betappUserRepository.existsByEmail(email)) {
      UserAlreadyExistsException.emailAlreadyExists(email);
    }
  }
}
