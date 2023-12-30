package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.features.result.score.ScoreService;
import com.jeleniasty.betapp.httpclient.footballdata.ScoreResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

  private final ResultRepository resultRepository;
  private final ScoreService scoreService;

  public Result addResult(ResultDTO resultDTO) {
    return resultRepository.save(
      new Result(
        resultDTO.winner(),
        resultDTO.duration(),
        scoreService.getScore(resultDTO.halfTimeScore()),
        scoreService.getScore(resultDTO.regularTimeScore()),
        scoreService.getScore(resultDTO.extraTimeScore()),
        scoreService.getScore(resultDTO.penaltiesScore()),
        scoreService.getScore(resultDTO.fullTimeScore())
      )
    );
  }

  //TODO add result scores validation based on duration

  public ResultDTO mapToDTO(ScoreResponse scoreResponse) {
    ScoreDTO regularTime = null;
    ScoreDTO extraTime = null;
    ScoreDTO penalties = null;

    switch (scoreResponse.duration()) {
      case REGULAR -> {}
      case EXTRA -> {
        regularTime =
          new ScoreDTO(
            scoreResponse.regularTime().home(),
            scoreResponse.regularTime().away()
          );
        extraTime =
          new ScoreDTO(
            scoreResponse.extraTime().home(),
            scoreResponse.extraTime().away()
          );
      }
      case PENALTY_SHOOTOUT -> {
        regularTime =
          new ScoreDTO(
            scoreResponse.regularTime().home(),
            scoreResponse.regularTime().away()
          );
        extraTime =
          new ScoreDTO(
            scoreResponse.extraTime().home(),
            scoreResponse.extraTime().away()
          );

        penalties =
          new ScoreDTO(
            scoreResponse.penalties().home(),
            scoreResponse.penalties().away()
          );
      }
    }
    return new ResultDTO(
      scoreResponse.winner(),
      scoreResponse.duration(),
      new ScoreDTO(
        scoreResponse.halfTime().home(),
        scoreResponse.halfTime().away()
      ),
      regularTime,
      extraTime,
      penalties,
      new ScoreDTO(
        scoreResponse.fullTime().home(),
        scoreResponse.fullTime().away()
      )
    );
  }

  public Optional<ResultDTO> mapToDTO(Result result) {
    ScoreDTO halfTime = null;
    ScoreDTO regularTime = null;
    ScoreDTO extraTime = null;
    ScoreDTO penalties = null;
    ScoreDTO fullTime = null;

    if (result == null) {
      return Optional.empty();
    }

    if (result.getDuration() == null) {
      return Optional.of(
        new ResultDTO(result.getWinner(), null, null, null, null, null, null)
      );
    }

    switch (result.getDuration()) {
      case REGULAR -> {
        if (result.getHalfTimeScore() != null) {
          halfTime =
            new ScoreDTO(
              result.getHalfTimeScore().getHome(),
              result.getHalfTimeScore().getAway()
            );
        }

        fullTime =
          new ScoreDTO(
            result.getFullTimeScore().getHome(),
            result.getFullTimeScore().getAway()
          );
      }
      case EXTRA -> {
        regularTime =
          new ScoreDTO(
            result.getRegularTimeScore().getHome(),
            result.getRegularTimeScore().getAway()
          );
        extraTime =
          new ScoreDTO(
            result.getExtraTimeScore().getHome(),
            result.getExtraTimeScore().getAway()
          );
      }
      case PENALTY_SHOOTOUT -> {
        regularTime =
          new ScoreDTO(
            result.getRegularTimeScore().getHome(),
            result.getRegularTimeScore().getAway()
          );
        extraTime =
          new ScoreDTO(
            result.getExtraTimeScore().getHome(),
            result.getExtraTimeScore().getAway()
          );

        penalties =
          new ScoreDTO(
            result.getPenaltiesScore().getHome(),
            result.getPenaltiesScore().getAway()
          );
      }
    }
    return Optional.of(
      new ResultDTO(
        result.getWinner(),
        result.getDuration(),
        halfTime,
        regularTime,
        extraTime,
        penalties,
        fullTime
      )
    );
  }
}
