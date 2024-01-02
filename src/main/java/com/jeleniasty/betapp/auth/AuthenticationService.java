package com.jeleniasty.betapp.auth;

import com.jeleniasty.betapp.features.user.BetappUser;
import com.jeleniasty.betapp.features.user.BetappUserRepository;
import com.jeleniasty.betapp.features.user.BetappUserService;
import com.jeleniasty.betapp.features.user.role.RoleService;
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
  private final BetappUserService betappUserService;

  public AuthResponse register(RegisterRequest request) {
    var jwtToken = jwtService.generateToken(
      betappUserService.registerPlayer(request)
    );
    return AuthResponse.builder().token(jwtToken).build();
  }

  public AuthResponse authenticate(AuthRequest request) {
    authManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.email(),
        request.password()
      )
    );
    var jwtToken = jwtService.generateToken(
      betappUserService.fetchUser(request.email())
    );
    return AuthResponse.builder().token(jwtToken).build();
  }
}
