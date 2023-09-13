package com.jeleniasty.betapp.security.auth;

import com.jeleniasty.betapp.features.user.repository.BetappUserRepository;
import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import com.jeleniasty.betapp.features.user.repository.entity.BetappUserRole;
import com.jeleniasty.betapp.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final BetappUserRepository betappUserRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = BetappUser
      .builder()
      .username(request.getUsername())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .betappUserRole(BetappUserRole.USER)
      .build();
    betappUserRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );

    var user = betappUserRepository
      .findByEmail(request.getEmail())
      .orElseThrow();

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }
}
