package com.jeleniasty.betapp.features.scheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.jeleniasty.betapp.config.OddsApiProperties;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.odds.MatchOddsDTO;
import com.jeleniasty.betapp.features.odds.OddsService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class OddsSchedulerTest {

  @Mock
  private OddsService oddsService;

  @Mock
  private OddsApiProperties oddsApiProperties;

  @Autowired
  private MatchService matchService;

  private OddsScheduler oddsScheduler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    oddsScheduler =
      new OddsScheduler(oddsService, oddsApiProperties, matchService);
  }

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
    "postgres:16.0"
  );

  @Test
  @Sql({ "/insert_competition.sql" })
  @Transactional
  void setOdds() {
    //arrange
    when(oddsApiProperties.getCompetitionKey())
      .thenReturn(Collections.singletonMap("key", "value"));

    var match = this.matchService.findMatch(3L);
    var homeOdds = 3.34f;
    var awayOdds = 1.66f;
    var drawOdds = 1.57f;
    when(oddsService.getCompetitionOdds(anyString()))
      .thenReturn(
        List.of(
          new MatchOddsDTO(
            match.getHomeTeam().getName(),
            match.getAwayTeam().getName(),
            match.getDate(),
            homeOdds,
            awayOdds,
            drawOdds
          )
        )
      );

    //act
    this.oddsScheduler.setOdds();

    //assert
    assertThat(this.matchService.findMatch(3L).getHomeOdds())
      .isEqualTo(homeOdds);
    assertThat(this.matchService.findMatch(3L).getAwayOdds())
      .isEqualTo(awayOdds);
    assertThat(this.matchService.findMatch(3L).getDrawOdds())
      .isEqualTo(drawOdds);
  }
}
