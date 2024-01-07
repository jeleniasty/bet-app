package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.result.score.Score;
import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.features.result.score.ScoreService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

  private final ResultRepository resultRepository;
  private final ScoreService scoreService;

  public Result saveResult(ResultDTO resultDTO) {
    Score halfTimeScore = null;
    Score regularTimeScore = null;
    Score extraTimeScore = null;
    Score penaltiesScore = null;
    Score fullTimeScore = null;

    if (resultDTO.halfTimeScore() != null) {
      halfTimeScore = scoreService.getScore(resultDTO.halfTimeScore());
    }

    if (resultDTO.regularTimeScore() != null) {
      regularTimeScore = scoreService.getScore(resultDTO.regularTimeScore());
    }

    if (resultDTO.extraTimeScore() != null) {
      extraTimeScore = scoreService.getScore(resultDTO.extraTimeScore());
    }

    if (resultDTO.penaltiesScore() != null) {
      penaltiesScore = scoreService.getScore(resultDTO.penaltiesScore());
    }

    if (resultDTO.fullTimeScore() != null) {
      fullTimeScore = scoreService.getScore(resultDTO.fullTimeScore());
    }

    return resultRepository.save(
      new Result(
        resultDTO.winner(),
        resultDTO.duration(),
        halfTimeScore,
        regularTimeScore,
        extraTimeScore,
        penaltiesScore,
        fullTimeScore
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
      return Optional.empty();
    }

    if (result.getHalfTimeScore() != null) {
      halfTime =
        new ScoreDTO(
          result.getHalfTimeScore().getHome(),
          result.getHalfTimeScore().getAway()
        );
    }

    if (result.getRegularTimeScore() != null) {
      regularTime =
        new ScoreDTO(
          result.getRegularTimeScore().getHome(),
          result.getRegularTimeScore().getAway()
        );
    }

    if (result.getExtraTimeScore() != null) {
      extraTime =
        new ScoreDTO(
          result.getExtraTimeScore().getHome(),
          result.getExtraTimeScore().getAway()
        );
    }

    if (result.getPenaltiesScore() != null) {
      penalties =
        new ScoreDTO(
          result.getPenaltiesScore().getHome(),
          result.getPenaltiesScore().getAway()
        );
    }

    if (result.getFullTimeScore() != null) {
      fullTime =
        new ScoreDTO(
          result.getFullTimeScore().getHome(),
          result.getFullTimeScore().getAway()
        );
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
