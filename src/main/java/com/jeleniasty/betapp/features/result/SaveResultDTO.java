package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.score.ScoreDTO;

public record SaveResultDTO(
  Winner winner,
  Duration duration,
  ScoreDTO halfTimeScore,
  ScoreDTO regularTimeScore,
  ScoreDTO extraTimeScore,
  ScoreDTO penaltiesScore
) {}
