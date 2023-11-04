package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.MatchResultDTO;
import jakarta.validation.Valid;

public record CreateBetDTO(
  @Valid MatchResultDTO matchResultDTO,
  BetType betType,
  Long matchId
) {}
