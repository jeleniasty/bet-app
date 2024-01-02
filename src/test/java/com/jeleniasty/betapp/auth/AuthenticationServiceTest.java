package com.jeleniasty.betapp.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jeleniasty.betapp.features.exceptions.UserAlreadyExistsException;
import com.jeleniasty.betapp.features.user.BetappUserService;
import com.jeleniasty.betapp.features.user.role.RoleName;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class AuthenticationServiceTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
    "postgres:16.0"
  );

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private BetappUserService betappUserService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  @Transactional
  void register_method_should_save_new_user_in_database_and_return_jwt_token() {
    //arrange
    var expectedBasePoints = 0.00d;
    var registerRequest = new RegisterRequest(
      "test_user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );

    //act
    var token = authenticationService.register(registerRequest);

    //assert
    var betappUserFromDb = betappUserService.fetchUser(registerRequest.email());

    assertThat(betappUserFromDb.getUsername())
      .isEqualTo(registerRequest.username());
    assertThat(betappUserFromDb.getEmail()).isEqualTo(registerRequest.email());
    assertThat(betappUserFromDb.getPoints()).isEqualTo(expectedBasePoints);
    assertThat(
      passwordEncoder.matches(
        registerRequest.password(),
        betappUserFromDb.getPassword()
      )
    )
      .isTrue();
    assertThat(token.token()).isNotEmpty();
  }

  @Test
  @Transactional
  void register_method_using_existing_username_should_throw_UserAlreadyExistsException() {
    //arrange
    var registerRequest = new RegisterRequest(
      "test_user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );
    authenticationService.register(registerRequest);

    var existingUsernameRegisterRequest = new RegisterRequest(
      "test_user",
      "test_1@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );

    //act & assert
    assertThrows(
      UserAlreadyExistsException.class,
      () -> authenticationService.register(existingUsernameRegisterRequest)
    );
  }

  @Test
  @Transactional
  void register_method_using_existing_email_should_throw_UserAlreadyExistsException() {
    //arrange
    var registerRequest = new RegisterRequest(
      "test_user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );
    authenticationService.register(registerRequest);

    var existingUsernameRegisterRequest = new RegisterRequest(
      "test_user_1",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );

    //act & assert
    assertThrows(
      UserAlreadyExistsException.class,
      () -> authenticationService.register(existingUsernameRegisterRequest)
    );
  }

  @Test
  @Transactional
  void authenticate_method_should_return_jwt_token() {
    //arrange
    var registerRequest = new RegisterRequest(
      "test_user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );
    authenticationService.register(registerRequest);

    var authRequest = new AuthRequest(
      registerRequest.email(),
      registerRequest.password()
    );

    //act
    var token = authenticationService.authenticate(authRequest);

    //assert
    assertThat(token.token()).isNotEmpty();
  }

  @Test
  @Transactional
  void authenticate_method_using_wrong_email_should_throw_bad_credentials_exception() {
    //arrange
    var registerRequest = new RegisterRequest(
      "test_user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );
    authenticationService.register(registerRequest);

    var authRequest = new AuthRequest(
      "wrong@email.com",
      registerRequest.password()
    );

    //act & assert
    assertThrows(
      BadCredentialsException.class,
      () -> authenticationService.authenticate(authRequest)
    );
  }

  @Test
  @Transactional
  void authenticate_method_using_wrong_password_should_throw_bad_credentials_exception() {
    //arrange
    var registerRequest = new RegisterRequest(
      "test_user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );
    authenticationService.register(registerRequest);

    var authRequest = new AuthRequest(registerRequest.email(), "wrongpassword");

    //act & assert
    assertThrows(
      BadCredentialsException.class,
      () -> authenticationService.authenticate(authRequest)
    );
  }
}
