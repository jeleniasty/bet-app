package com.jeleniasty.betapp.security.auth;

import com.jeleniasty.betapp.features.role.RoleService;
import com.jeleniasty.betapp.features.user.BetappUser;
import com.jeleniasty.betapp.features.user.BetappUserRepository;
import com.jeleniasty.betapp.security.config.JwtService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  private final BetappUserRepository betappUserRepository;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = new BetappUser();
    user.setUsername(request.username());
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRoles(
      request
        .roleNames()
        .stream()
        .map(roleService::findRoleByName)
        .collect(Collectors.toSet())
    );

    betappUserRepository.save(user);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.email(),
        request.password()
      )
    );

    var user = betappUserRepository
      .findByUsernameOrEmail(request.email(), request.email())
      .orElseThrow();

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }
}
