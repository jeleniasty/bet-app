package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.result.MatchResultDTO;

public record SaveMatchResultDTO(MatchResultDTO matchResultDTO, Long matchId) {}
