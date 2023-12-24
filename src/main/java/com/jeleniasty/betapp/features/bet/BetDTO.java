package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.ResultDTO;
import java.time.LocalDateTime;

public record BetDTO(
  long id,
  long matchId,
  BetType type,
  ResultDTO result,
  LocalDateTime creationDate
) {}
