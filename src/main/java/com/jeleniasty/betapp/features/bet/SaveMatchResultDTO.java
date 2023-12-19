package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.ResultDTO;

public record SaveMatchResultDTO(
  ResultDTO resultDTO,
  Long matchId,
  Long externalId,
  MatchStatus status
) {}
