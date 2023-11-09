package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.score.Score;
import java.time.LocalDateTime;

public record BetDTO(
  long id,
  BetType type,
  Winner winner,
  Duration matchDuration,

  Score halfTimeScore,
  Score regularTimeScore,
  Score extraTimeScore,
  Score penaltiesScore,
  LocalDateTime creationDate
) {}
