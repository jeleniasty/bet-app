package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.score.ScoreDTO;
import jakarta.validation.Valid;

public record SaveResultDTO(
  Winner winner,
  Duration duration,
  @Valid ScoreDTO halfTimeScore,
  @Valid ScoreDTO regularTimeScore,
  @Valid ScoreDTO extraTimeScore,
  @Valid ScoreDTO penaltiesScore
) {}
