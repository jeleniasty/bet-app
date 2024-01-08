package com.jeleniasty.betapp.features.competition;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.result.ResultDTO;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.features.team.TeamDTO;
import com.jeleniasty.betapp.httpclient.footballdata.CompetitionDeserializer;
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

    var competitionDTO = constructTestCompetitionMatchesResponse();

    Mockito
      .when(competitionHttpClient.getCompetitionMatchesData(competitionRequest))
      .thenReturn(competitionDTO);

    //act
    competitionService.createNewCompetition(competitionRequest);

    //assert
    var competition = competitionRepository
      .findCompetitionByCodeAndSeason(
        competitionRequest.code(),
        competitionRequest.season()
      )
      .get();

    assertThat(competition.getCompetitionMatches().size())
      .isEqualTo(competitionDTO.matches().size());

    assertThat(
      getMatchesWithStatus(
        competition.getCompetitionMatches(),
        MatchStatus.FINISHED
      )
    )
      .isEqualTo(
        getMatchesWithStatus(competitionDTO.matches(), MatchStatus.FINISHED)
      );

    assertThat(
      getMatchesWithStatus(
        competition.getCompetitionMatches(),
        MatchStatus.SCHEDULED
      )
    )
      .isEqualTo(
        getMatchesWithStatus(competitionDTO.matches(), MatchStatus.SCHEDULED)
      );

    assertThat(
      getMatchesWithStatus(
        competition.getCompetitionMatches(),
        MatchStatus.TIMED
      )
    )
      .isEqualTo(
        getMatchesWithStatus(competitionDTO.matches(), MatchStatus.TIMED)
      );
  }

  private int getMatchesWithStatus(List<MatchDTO> matches, MatchStatus status) {
    return matches
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

  private CompetitionDTO constructTestCompetitionMatchesResponse() {
    return new CompetitionDTO(
      null,
      "Test competition",
      "TST",
      CompetitionType.LEAGUE,
      2023,
      "test competition emblem url",
      LocalDate.now().minusMonths(5),
      LocalDate.now().plusMonths(5),
      List.of(
        new MatchDTO(
          10001L,
          new TeamDTO(null, "Test team 1", "TS1", "test crest url1"),
          new TeamDTO(null, "Test team 2", "TS2", "test emblem url2"),
          1f,
          1f,
          1f,
          MatchStatus.FINISHED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minusMonths(2),
          new ResultDTO(
            Winner.HOME_TEAM,
            Duration.REGULAR,
            new ScoreDTO(0, 1),
            null,
            null,
            null,
            new ScoreDTO(0, 0)
          ),
          10001L
        ),
        new MatchDTO(
          10002L,
          new TeamDTO(null, "Test team 3", "TS3", "test crest url3"),
          new TeamDTO(null, "Test team 4", "TS4", "test emblem url4"),
          1f,
          1f,
          1f,
          MatchStatus.FINISHED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minusDays(10),
          new ResultDTO(
            Winner.DRAW,
            Duration.REGULAR,
            new ScoreDTO(1, 0),
            new ScoreDTO(1, 1),
            null,
            null,
            null
          ),
          10002L
        ),
        new MatchDTO(
          10003L,
          new TeamDTO(null, "Test team 5", "TS5", "test crest url5"),
          new TeamDTO(null, "Test team 6", "TS6", "test emblem url6"),
          1f,
          1f,
          1f,
          MatchStatus.TIMED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(14),
          null,
          10003L
        ),
        new MatchDTO(
          10004L,
          new TeamDTO(null, "Test team 7", "TS7", "test crest url7"),
          new TeamDTO(null, "Test team 8", "TS8", "test emblem url8"),
          1f,
          1f,
          1f,
          MatchStatus.SCHEDULED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusDays(14),
          null,
          10004L
        ),
        new MatchDTO(
          10005L,
          null,
          null,
          1f,
          1f,
          1f,
          MatchStatus.SCHEDULED,
          CompetitionStage.REGULAR_SEASON,
          null,
          LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).plusMonths(2),
          null,
          10005L
        )
      )
    );
  }
}
