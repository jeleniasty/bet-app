package com.jeleniasty.betapp.features.result;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeleniasty.betapp.features.result.score.Score;
import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.httpclient.footballdata.ScoreResponse;
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
class ResultServiceTest {

  @Autowired
  private ResultRepository resultRepository;

  @Autowired
  private ResultService resultService;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
    "postgres:16.0"
  );

  @Test
  @Transactional
  void addResult_should_save_record_in_database() {
    //arrange
    var resultDTO = new ResultDTO(
      Winner.HOME_TEAM,
      Duration.REGULAR,
      new ScoreDTO(0, 0),
      null,
      null,
      null,
      new ScoreDTO(1, 0)
    );

    //act
    var expectedResult = resultService.addResult(resultDTO);

    //assert
    var actualResult = resultRepository.findById(expectedResult.getId());
    var savedResultsCount = resultRepository.findAll().size();

    assertThat(savedResultsCount).isEqualTo(1);
    assertThat(expectedResult.equals(actualResult.get())).isTrue();
  }

  @Test
  void mapToDTO_given_Result_entity_should_return_ResultDTO() {
    //arrange
    var resultEntity = new Result(
      Winner.HOME_TEAM,
      Duration.REGULAR,
      new Score(0, 0),
      null,
      null,
      null,
      new Score(1, 0)
    );

    //act
    var actualResultDTO = resultService.mapToDTO(resultEntity);

    assertThat(resultEntity.getWinner())
      .isEqualTo(actualResultDTO.get().winner());
    assertThat(resultEntity.getDuration())
      .isEqualTo(actualResultDTO.get().duration());

    assertThat(resultEntity.getHalfTimeScore().getHome())
      .isEqualTo(actualResultDTO.get().halfTimeScore().home());
    assertThat(resultEntity.getHalfTimeScore().getAway())
      .isEqualTo(actualResultDTO.get().halfTimeScore().away());

    assertThat(resultEntity.getRegularTimeScore()).isNull();
    assertThat(resultEntity.getExtraTimeScore()).isNull();
    assertThat(resultEntity.getPenaltiesScore()).isNull();

    assertThat(resultEntity.getFullTimeScore().getHome())
      .isEqualTo(actualResultDTO.get().fullTimeScore().home());
    assertThat(resultEntity.getFullTimeScore().getAway())
      .isEqualTo(actualResultDTO.get().fullTimeScore().away());
  }

  @Test
  void mapToDTO_given_null_Result_entity_should_return_empty_Optional() {
    //arrange
    Result resultEntity = null;

    //act
    var actualResultDTO = resultService.mapToDTO(resultEntity);

    //assert
    assertThat(actualResultDTO.isPresent()).isFalse();
  }

  @Test
  void mapToDTO_given_ScoreResponse_should_return_ResultDTO() {
    //arrange
    var scoreResponse = new ScoreResponse(
      Winner.HOME_TEAM,
      Duration.REGULAR,
      new ScoreResponse.BasicScoreResponse(0, 0),
      new ScoreResponse.BasicScoreResponse(1, 0),
      null,
      null,
      null
    );

    //act
    var actualResultDTO = resultService.mapToDTO(scoreResponse);

    assertThat(scoreResponse.winner()).isEqualTo(actualResultDTO.winner());
    assertThat(scoreResponse.duration()).isEqualTo(actualResultDTO.duration());

    assertThat(scoreResponse.halfTime().home())
      .isEqualTo(actualResultDTO.halfTimeScore().home());
    assertThat(scoreResponse.halfTime().away())
      .isEqualTo(actualResultDTO.halfTimeScore().away());

    assertThat(scoreResponse.regularTime()).isNull();
    assertThat(scoreResponse.extraTime()).isNull();
    assertThat(scoreResponse.penalties()).isNull();

    assertThat(scoreResponse.fullTime().home())
      .isEqualTo(actualResultDTO.fullTimeScore().home());
    assertThat(scoreResponse.fullTime().away())
      .isEqualTo(actualResultDTO.fullTimeScore().away());
  }
}
