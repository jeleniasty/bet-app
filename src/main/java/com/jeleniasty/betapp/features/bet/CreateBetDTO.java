package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.SaveResultDTO;
import jakarta.validation.Valid;

public record CreateBetDTO(
  @Valid SaveResultDTO saveResultDTO,
  BetType betType,
  Long matchId
) {}
