package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.ResultDTO;

public record SaveMatchResultDTO(ResultDTO resultDTO, Long matchId) {}
