package com.jeleniasty.betapp.features.result.score;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {

  private final ScoreRepository scoreRepository;

  public Score saveScore(ScoreDTO scoreDTO) {
    return Optional
      .ofNullable(scoreDTO)
      .map(score -> new Score(score.home(), score.away()))
      .map(scoreRepository::save)
      .orElse(null);
  }
}
