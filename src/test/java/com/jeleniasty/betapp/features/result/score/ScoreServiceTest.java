package com.jeleniasty.betapp.features.result.score;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class ScoreServiceTest {

  @Autowired
  private ScoreRepository scoreRepository;

  @Autowired
  private ScoreService scoreService;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
    "postgres:16.0"
  );

  @Test
  void saveScore() {
    assertThat(postgreSQLContainer.isCreated()).isTrue();
    assertThat(postgreSQLContainer.isRunning()).isTrue();

    //arrange
    var scoreDTO = new ScoreDTO(1, 1);

    //act
    scoreService.fetchOrSave(scoreDTO);

    //assert
    var actualScore = scoreRepository.findByHomeAndAway(1, 1);

    assertThat(actualScore.get().getHome()).isEqualTo(scoreDTO.home());
    assertThat(actualScore.get().getAway()).isEqualTo(scoreDTO.away());
  }
}
