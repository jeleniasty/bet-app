package com.jeleniasty.betapp.features.result.score;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
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
  @Transactional
  void getScore_should_save_new_score_in_db_if_not_exists() {
    //arrange
    var scoreDTO = new ScoreDTO(1, 1);

    //act
    scoreService.getScore(scoreDTO);

    //assert
    var actualScore = scoreRepository.findByHomeAndAway(1, 1);
    var savedScoresCount = scoreRepository.findAll().size();

    assertThat(savedScoresCount).isEqualTo(1);
    assertThat(actualScore.get().getHome()).isEqualTo(scoreDTO.home());
    assertThat(actualScore.get().getAway()).isEqualTo(scoreDTO.away());
  }

  @Test
  @Transactional
  void getScore_should_get_score_from_db_if_exists() {
    //arrange
    var scoreDTO = new ScoreDTO(1, 1);

    //act
    scoreRepository.save(new Score(scoreDTO.home(), scoreDTO.away()));
    var actualScore = scoreService.getScore(scoreDTO);

    //assert
    var savedScoresCount = scoreRepository.findAll().size();

    assertThat(savedScoresCount).isEqualTo(1);
    assertThat(actualScore.getHome()).isEqualTo(scoreDTO.home());
    assertThat(actualScore.getAway()).isEqualTo(scoreDTO.away());
  }
}
