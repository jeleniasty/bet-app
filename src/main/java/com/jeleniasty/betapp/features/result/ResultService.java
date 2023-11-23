package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.features.result.score.ScoreService;
import com.jeleniasty.betapp.httpclient.footballdata.ScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

  private final ResultRepository resultRepository;
  private final ScoreService scoreService;

  public Result saveResult(ResultDTO resultDTO) {
    return resultRepository.save(
      new Result(
        resultDTO.winner(),
        resultDTO.duration(),
        scoreService.saveScore(resultDTO.halfTimeScore()),
        scoreService.saveScore(resultDTO.regularTimeScore()),
        scoreService.saveScore(resultDTO.extraTimeScore()),
        scoreService.saveScore(resultDTO.penaltiesScore()),
        scoreService.saveScore(resultDTO.fullTimeScore())
      )
    );
  }

  public ResultDTO mapToDTO(ScoreResponse scoreResponse) {
    return new ResultDTO(
      scoreResponse.winner(),
      scoreResponse.duration(),
      new ScoreDTO(
        scoreResponse.halfTime().home(),
        scoreResponse.halfTime().away()
      ),
      new ScoreDTO(
        scoreResponse.regularTime().home(),
        scoreResponse.regularTime().away()
      ),
      new ScoreDTO(
        scoreResponse.extraTime().home(),
        scoreResponse.extraTime().away()
      ),
      new ScoreDTO(
        scoreResponse.penalties().home(),
        scoreResponse.penalties().away()
      ),
      new ScoreDTO(
        scoreResponse.fullTime().home(),
        scoreResponse.fullTime().away()
      )
    );
  }
}
