package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.score.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

  private final ResultRepository resultRepository;
  private final ScoreService scoreService;

  public Result saveResult(SaveResultDTO saveResultDTO) {
    return resultRepository.save(
      new Result(
        saveResultDTO.winner(),
        saveResultDTO.duration(),
        scoreService.saveScore(saveResultDTO.halfTimeScore()),
        scoreService.saveScore(saveResultDTO.regularTimeScore()),
        scoreService.saveScore(saveResultDTO.extraTimeScore()),
        scoreService.saveScore(saveResultDTO.penaltiesScore())
      )
    );
  }
}
