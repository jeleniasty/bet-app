package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.score.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

  private final ResultRepository resultRepository;
  private final ScoreService scoreService;

  public Result saveResult(MatchResultDTO matchResultDTO) {
    return resultRepository.save(
      new Result(
        matchResultDTO.winner(),
        matchResultDTO.duration(),
        scoreService.saveScore(matchResultDTO.halfTimeScore()),
        scoreService.saveScore(matchResultDTO.regularTimeScore()),
        scoreService.saveScore(matchResultDTO.extraTimeScore()),
        scoreService.saveScore(matchResultDTO.penaltiesScore()),
        scoreService.saveScore(matchResultDTO.fullTimeScore())
      )
    );
  }
}
