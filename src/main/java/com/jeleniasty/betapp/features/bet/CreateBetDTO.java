package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.ResultDTO;
import jakarta.validation.Valid;

public record CreateBetDTO(
  @Valid ResultDTO resultDTO,
  BetType betType,
  long matchId
) {}
