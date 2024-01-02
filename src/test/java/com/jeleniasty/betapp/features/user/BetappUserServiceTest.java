package com.jeleniasty.betapp.features.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeleniasty.betapp.TestHelpers;
import com.jeleniasty.betapp.auth.RegisterRequest;
import com.jeleniasty.betapp.features.user.role.RoleName;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class BetappUserServiceTest {

  @Autowired
  private BetappUserService betappUserService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TestHelpers testHelpers;

  @MockBean
  private Authentication authentication;

  @MockBean
  private SecurityContext securityContext;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
    "postgres:16.0"
  );

  @Test
  @Transactional
  void registerPlayer_should_save_and_return_new_entity() {
    //arrange
    var expectedBasePoints = 0.00d;
    var registerRequest = new RegisterRequest(
      "test_user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );

    //act
    var newUser = betappUserService.registerPlayer(registerRequest);

    //assert
    assertThat(newUser.getEmail()).isEqualTo(registerRequest.email());
    assertThat(newUser.getUsername()).isEqualTo(registerRequest.username());
    assertThat(
      passwordEncoder.matches(registerRequest.password(), newUser.getPassword())
    )
      .isTrue();
    assertThat(newUser.getPoints()).isEqualTo(expectedBasePoints);
    assertThat(newUser.getRoles().size())
      .isEqualTo(registerRequest.roleNames().size());
  }

  @Test
  @Transactional
  void getCurrentUser_method_should_return_current_UserPrincipal() {
    //arrange
    var expectedCurrentUser = testHelpers.createTestPlayer();
    Mockito
      .when(securityContext.getAuthentication())
      .thenReturn(authentication);

    SecurityContextHolder.setContext(securityContext);
    Mockito
      .when(
        SecurityContextHolder.getContext().getAuthentication().getPrincipal()
      )
      .thenReturn(UserPrincipal.create(expectedCurrentUser));

    //act
    var actualCurrentUserPrincipal = betappUserService.getCurrentUser();

    //assert
    assertThat(expectedCurrentUser.getId())
      .isEqualTo(actualCurrentUserPrincipal.getId());
    assertThat(expectedCurrentUser.getEmail())
      .isEqualTo(actualCurrentUserPrincipal.getEmail());
    assertThat(expectedCurrentUser.getUsername())
      .isEqualTo(actualCurrentUserPrincipal.getUsername());
    assertThat(expectedCurrentUser.getPassword())
      .isEqualTo(actualCurrentUserPrincipal.getPassword());
  }

  @Test
  @Transactional
  void getPlayerScores_method_should_return_list_of_3_UserScoreDTO() {
    //arrange
    var testPlayers = testHelpers.createTestPlayers(3);

    //act
    var playerScores = betappUserService.getPlayerScores();

    //assert
    var testPlayer1 = testPlayers.get(0);
    var player1Score = playerScores.get(0);

    assertThat(playerScores.size()).isEqualTo(testPlayers.size());
    assertThat(player1Score.id()).isEqualTo(testPlayer1.getId());
    assertThat(player1Score.username()).isEqualTo(testPlayer1.getUsername());
    assertThat(player1Score.points()).isEqualTo(testPlayer1.getPoints());
  }

  @Test
  @Transactional
  void getPlayerScores_method_should_return_empty_list() {
    //act
    var playerScores = betappUserService.getPlayerScores();

    //assert
    assertThat(playerScores.size()).isEqualTo(0);
  }
}
