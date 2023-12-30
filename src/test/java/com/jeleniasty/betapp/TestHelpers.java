package com.jeleniasty.betapp;

import com.jeleniasty.betapp.auth.AuthenticationController;
import com.jeleniasty.betapp.auth.RegisterRequest;
import com.jeleniasty.betapp.features.competition.Competition;
import com.jeleniasty.betapp.features.competition.CompetitionType;
import com.jeleniasty.betapp.features.match.MatchRepository;
import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.team.Team;
import com.jeleniasty.betapp.features.user.BetappUser;
import com.jeleniasty.betapp.features.user.BetappUserRepository;
import com.jeleniasty.betapp.features.user.role.RoleName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestHelpers {

  private final AuthenticationController authenticationController;
  private final BetappUserRepository betappUserRepository;
  private final MatchRepository matchRepository;

  public BetappUser createTestPlayer() {
    var registerRequest = new RegisterRequest(
      "test user",
      "test@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );
    authenticationController.register(registerRequest);
    return betappUserRepository
      .findByUsernameOrEmail(
        registerRequest.username(),
        registerRequest.email()
      )
      .get();
  }

  public List<BetappUser> createTestPlayers(int numberOfPlayers) {
    List<BetappUser> users = new ArrayList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      var registerRequest = new RegisterRequest(
        "test user " + i,
        "test@email.com " + i,
        "password",
        Set.of(RoleName.PLAYER)
      );
      authenticationController.register(registerRequest);
      users.add(
        betappUserRepository
          .findByUsernameOrEmail(
            registerRequest.username(),
            registerRequest.email()
          )
          .get()
      );
    }
    return users;
  }

  public Match createFutureMatchWithoutResult() {
    var match = new Match(
      MatchStatus.TIMED,
      CompetitionStage.REGULAR_SEASON,
      null,
      3.23f,
      2.04f,
      1.53f,
      LocalDateTime.now().plusDays(14),
      19L
    );
    match.assignCompetition(createTestCompetition());
    var teams = createTestTeams(2);
    match.setHomeTeam(teams.get(0));
    match.setHomeTeam(teams.get(1));

    return matchRepository.save(match);
  }

  public Match createPastMatchWithoutResult() {
    var match = new Match(
      MatchStatus.TIMED,
      CompetitionStage.REGULAR_SEASON,
      null,
      3.23f,
      2.04f,
      1.53f,
      LocalDateTime.now().minusDays(14),
      19L
    );
    match.assignCompetition(createTestCompetition());
    var teams = createTestTeams(2);
    match.setHomeTeam(teams.get(0));
    match.setHomeTeam(teams.get(1));

    return matchRepository.save(match);
  }

  public Match saveMatchResult(Match match, Result result) {
    match.setResult(result);
    return matchRepository.save(match);
  }

  private List<Team> createTestTeams(int numberOfTeams) {
    List<Team> testTeams = new ArrayList<>();

    for (int i = 0; i < numberOfTeams; i++) {
      testTeams.add(new Team("Test team " + i, "TT" + i, "test flag url " + i));
    }
    return testTeams;
  }

  private Competition createTestCompetition() {
    return new Competition(
      "Test competition",
      "TST",
      CompetitionType.LEAGUE,
      2023,
      "emblem url",
      LocalDate.now().minusMonths(5),
      LocalDate.now().plusMonths(5)
    );
  }
}
