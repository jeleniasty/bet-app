package com.jeleniasty.betapp.features.competition;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.httpclient.footballdata.CompetitionMatchesResponse;
import com.jeleniasty.betapp.httpclient.footballdata.MatchResponse;
import com.jeleniasty.betapp.httpclient.footballdata.ScoreResponse;
import com.jeleniasty.betapp.httpclient.footballdata.TeamResponse;
import com.jeleniasty.betapp.httpclient.footballdata.competition.CompetitionHttpClient;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class CompetitionServiceTest {

  @Autowired
  private CompetitionService competitionService;

  @Autowired
  private CompetitionRepository competitionRepository;

  @MockBean
  private CompetitionHttpClient competitionHttpClient;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
    "postgres:16.0"
  );

  @Test
  @Transactional
  void createNewCompetition_should_save_competition_and_its_matches_with_assigned_teams_to_database() {
    //arrange
    var competitionRequest = new CreateCompetitonRequest("TST", 2023);

    var competitionMatchesResponse = constructTestCompetitionMatchesResponse();

    Mockito
      .when(competitionHttpClient.getCompetitionMatchesData(competitionRequest))
      .thenReturn(competitionMatchesResponse);

    //act
    competitionService.createOrUpdateCompetition(competitionRequest);

    //assert
    var competition = competitionRepository
      .findCompetitionByCodeAndSeason(
        competitionRequest.code(),
        competitionRequest.season()
      )
      .get();

    assertThat(competition.getCompetitionMatches().size())
      .isEqualTo(
        getMatchesWithTeamsAssigned(competitionMatchesResponse).size()
      );

    assertThat(
      getMatchesWithStatus(
        competition.getCompetitionMatches(),
        MatchStatus.FINISHED
      )
    )
      .isEqualTo(
        getMatchesWithStatus(
          competitionMatchesResponse.matches(),
          MatchStatus.FINISHED
        )
      );

    assertThat(
      getMatchesWithStatus(
        competition.getCompetitionMatches(),
        MatchStatus.SCHEDULED
      )
    )
      .isEqualTo(
        getMatchesWithStatus(
          getMatchesWithTeamsAssigned(competitionMatchesResponse),
          MatchStatus.SCHEDULED
        )
      );

    assertThat(
      getMatchesWithStatus(
        competition.getCompetitionMatches(),
        MatchStatus.TIMED
      )
    )
      .isEqualTo(
        getMatchesWithStatus(
          competitionMatchesResponse.matches(),
          MatchStatus.TIMED
        )
      );
  }

  private List<MatchResponse> getMatchesWithTeamsAssigned(
    CompetitionMatchesResponse competitionMatchesResponse
  ) {
    return competitionMatchesResponse
      .matches()
      .stream()
      .filter(areTeamsAssigned())
      .toList();
  }

  private Predicate<MatchResponse> areTeamsAssigned() {
    return matchResponse ->
      matchResponse.homeTeam().name() != null &&
      matchResponse.awayTeam().name() != null;
  }

  private int getMatchesWithStatus(
    List<MatchResponse> matchResponses,
    MatchStatus status
  ) {
    return matchResponses
      .stream()
      .filter(matchResponse -> matchResponse.status() == status)
      .toList()
      .size();
  }

  private int getMatchesWithStatus(Set<Match> matches, MatchStatus status) {
    return matches
      .stream()
      .filter(match -> match.getStatus() == status)
      .toList()
      .size();
  }

  private CompetitionMatchesResponse constructTestCompetitionMatchesResponse() {
    var nullScoreResponse = new ScoreResponse(
      null,
      Duration.REGULAR,
      new ScoreResponse.BasicScoreResponse(null, null),
      new ScoreResponse.BasicScoreResponse(null, null),
      null,
      null,
      null
    );
    return new CompetitionMatchesResponse(
      new CompetitionMatchesResponse.Filters(2023),
      new CompetitionMatchesResponse.ResultSet(
        5,
        LocalDate.now().minusMonths(5),
        LocalDate.now().plusMonths(5),
        2
      ),
      new CompetitionMatchesResponse.CompetitionResponse(
        "Test competition",
        "TST",
        CompetitionType.LEAGUE,
        "test competition emblem url"
      ),
      List.of(
        new MatchResponse(
          10001L,
          MatchStatus.FINISHED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minusMonths(2),
          new TeamResponse("Test team 1", "TS1", "test crest url1"),
          new TeamResponse("Test team 2", "TS2", "test emblem url2"),
          new ScoreResponse(
            Winner.HOME_TEAM,
            Duration.REGULAR,
            new ScoreResponse.BasicScoreResponse(0, 1),
            new ScoreResponse.BasicScoreResponse(0, 0),
            null,
            null,
            null
          )
        ),
        new MatchResponse(
          10002L,
          MatchStatus.FINISHED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minusDays(10),
          new TeamResponse("Test team 3", "TS3", "test crest url3"),
          new TeamResponse("Test team 4", "TS4", "test emblem url4"),
          new ScoreResponse(
            Winner.DRAW,
            Duration.REGULAR,
            new ScoreResponse.BasicScoreResponse(1, 0),
            new ScoreResponse.BasicScoreResponse(1, 1),
            null,
            null,
            null
          )
        ),
        new MatchResponse(
          10003L,
          MatchStatus.TIMED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(14),
          new TeamResponse("Test team 5", "TS5", "test crest url5"),
          new TeamResponse("Test team 6", "TS6", "test emblem url6"),
          nullScoreResponse
        ),
        new MatchResponse(
          10004L,
          MatchStatus.SCHEDULED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(14),
          new TeamResponse("Test team 7", "TS7", "test crest url7"),
          new TeamResponse("Test team 8", "TS8", "test emblem url8"),
          nullScoreResponse
        ),
        new MatchResponse(
          10005L,
          MatchStatus.SCHEDULED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusMonths(2),
          new TeamResponse(null, null, null),
          new TeamResponse(null, null, null),
          nullScoreResponse
        )
      )
    );
  }
}
