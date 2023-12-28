package com.jeleniasty.betapp.features.result.score;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {

  private final ScoreRepository scoreRepository;

  public Score fetchOrSave(ScoreDTO scoreDTO) {
    return Optional
      .ofNullable(scoreDTO)
      .map(score ->
        this.scoreRepository.findByHomeAndAway(scoreDTO.home(), scoreDTO.away())
          .orElseGet(() ->
            this.scoreRepository.save(
                new Score(scoreDTO.home(), scoreDTO.away())
              )
          )
      )
      .orElse(null);
  }
}
