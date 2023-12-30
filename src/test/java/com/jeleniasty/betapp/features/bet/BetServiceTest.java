package com.jeleniasty.betapp.features.bet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.jeleniasty.betapp.TestHelpers;
import com.jeleniasty.betapp.features.exceptions.PastMatchBetException;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.result.ResultDTO;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.result.score.Score;
import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.features.user.BetappUser;
import com.jeleniasty.betapp.features.user.BetappUserService;
import com.jeleniasty.betapp.features.user.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class BetServiceTest {

  @Autowired
  private BetappUserService betappUserService;

  @Autowired
  private BetService betService;

  @MockBean
  private Authentication authentication;

  @MockBean
  private SecurityContext securityContext;

  @Autowired
  private TestHelpers testHelpers;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
    "postgres:16.0"
  );

  @BeforeEach
  void setUp() {
    Mockito
      .when(securityContext.getAuthentication())
      .thenReturn(authentication);
  }

  @Test
  @Transactional
  void getCurrentUserBets() {
    //arrange
    var testUser = testHelpers.createTestPlayer();
    var testMatch = testHelpers.createFutureMatchWithoutResult();
    setCurrentUser(testUser);

    var fullTimeResultBetDTO = new CreateBetDTO(
      new ResultDTO(Winner.HOME_TEAM, null, null, null, null, null, null),
      BetType.FULL_TIME_RESULT,
      testMatch.getId()
    );
    var correctScoreBetDTO = new CreateBetDTO(
      new ResultDTO(
        Winner.HOME_TEAM,
        Duration.REGULAR,
        null,
        null,
        null,
        null,
        new ScoreDTO(2, 1)
      ),
      BetType.CORRECT_SCORE,
      testMatch.getId()
    );
    betService.createBet(correctScoreBetDTO);
    betService.createBet(fullTimeResultBetDTO);

    //act
    var currentUserBets = betService.getCurrentUserBets(testMatch.getId());

    //assert
    assertThat(currentUserBets.size()).isEqualTo(2);
  }

  @Test
  @Transactional
  void createBet_should_create_bet_record_in_database() {
    //arrange
    var testUser = testHelpers.createTestPlayer();
    var testMatch = testHelpers.createFutureMatchWithoutResult();

    setCurrentUser(testUser);

    var betDTO = new CreateBetDTO(
      new ResultDTO(Winner.HOME_TEAM, null, null, null, null, null, null),
      BetType.FULL_TIME_RESULT,
      testMatch.getId()
    );

    //act
    betService.createBet(betDTO);

    //assert
    var currentUserBets = betService.getCurrentUserBets(testMatch.getId());
    assertThat(currentUserBets.size()).isEqualTo(1);

    var createdBet = currentUserBets.get(0);
    assertThat(createdBet.matchId()).isEqualTo(testMatch.getId());
    assertThat(createdBet.type()).isEqualTo(betDTO.betType());
    assertThat(createdBet.result().winner())
      .isEqualTo(betDTO.resultDTO().winner());
  }

  @Test
  @Transactional
  void createBet_on_past_match_should_throw_PastMatchBetException() {
    //arrange
    var testUser = testHelpers.createTestPlayer();
    var testMatch = testHelpers.createPastMatchWithoutResult();

    setCurrentUser(testUser);

    var betDTO = new CreateBetDTO(
      new ResultDTO(Winner.HOME_TEAM, null, null, null, null, null, null),
      BetType.FULL_TIME_RESULT,
      testMatch.getId()
    );

    //act & assert
    assertThrows(
      PastMatchBetException.class,
      () -> betService.createBet(betDTO)
    );
  }

  @Test
  @Transactional
  void assignPoints_should_add_points_only_for_users_with_correct_fullTimeResult_bet() {
    //arrange
    var testUsers = testHelpers.createTestPlayers(2);
    var testUser1 = testUsers.get(0);
    var testUser2 = testUsers.get(1);
    var testMatch = testHelpers.createFutureMatchWithoutResult();

    setCurrentUser(testUser1);
    var user1BetDTO = new CreateBetDTO(
      new ResultDTO(Winner.HOME_TEAM, null, null, null, null, null, null),
      BetType.FULL_TIME_RESULT,
      testMatch.getId()
    );
    betService.createBet(user1BetDTO);

    setCurrentUser(testUser2);
    var user2BetDTO = new CreateBetDTO(
      new ResultDTO(Winner.AWAY_TEAM, null, null, null, null, null, null),
      BetType.FULL_TIME_RESULT,
      testMatch.getId()
    );
    betService.createBet(user2BetDTO);

    testHelpers.saveMatchResult(
      testMatch,
      new Result(Winner.AWAY_TEAM, null, null, null, null, null, null)
    );

    //act
    betService.assignPoints(testMatch.getId());

    //assert
    var loseUser = betappUserService.fetchUser(testUser1.getId());
    var winUser = betappUserService.fetchUser(testUser1.getId());

    assertThat(testUser1.getPoints()).isEqualTo(loseUser.getPoints());
    assertThat(testUser2.getPoints()).isNotEqualTo(winUser.getPoints());
  }

  @Test
  @Transactional
  void assignPoints_should_add_points_only_for_users_with_correct_correctScore_bet() {
    //arrange
    var testUsers = testHelpers.createTestPlayers(2);
    var testUser1 = testUsers.get(0);
    var testUser2 = testUsers.get(1);
    var testMatch = testHelpers.createFutureMatchWithoutResult();

    setCurrentUser(testUser1);
    var user1BetDTO = new CreateBetDTO(
      new ResultDTO(
        Winner.AWAY_TEAM,
        Duration.REGULAR,
        null,
        null,
        null,
        null,
        new ScoreDTO(0, 2)
      ),
      BetType.CORRECT_SCORE,
      testMatch.getId()
    );
    betService.createBet(user1BetDTO);
    setCurrentUser(testUser2);

    var user2BetDTO = new CreateBetDTO(
      new ResultDTO(
        Winner.HOME_TEAM,
        Duration.REGULAR,
        null,
        null,
        null,
        null,
        new ScoreDTO(2, 1)
      ),
      BetType.CORRECT_SCORE,
      testMatch.getId()
    );
    betService.createBet(user2BetDTO);
    testHelpers.saveMatchResult(
      testMatch,
      new Result(
        Winner.HOME_TEAM,
        Duration.REGULAR,
        new Score(0, 0),
        null,
        null,
        null,
        new Score(2, 1)
      )
    );

    //act
    betService.assignPoints(testMatch.getId());

    //assert
    var loseUser = betappUserService.fetchUser(testUser1.getId());

    var winUser = betappUserService.fetchUser(testUser1.getId());
    assertThat(testUser1.getPoints()).isEqualTo(loseUser.getPoints());

    assertThat(testUser2.getPoints()).isNotEqualTo(winUser.getPoints());
  }

  private void setCurrentUser(BetappUser testUser) {
    SecurityContextHolder.setContext(securityContext);
    Mockito
      .when(
        SecurityContextHolder.getContext().getAuthentication().getPrincipal()
      )
      .thenReturn(UserPrincipal.create(testUser));
  }
}
