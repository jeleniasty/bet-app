package com.jeleniasty.betapp.httpclient.footballdata;

import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.Group;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import java.time.LocalDateTime;

public record MatchResponse(
  Long id,
  MatchStatus status,
  CompetitionStage stage,
  Group group,
  LocalDateTime utcDate,
  TeamResponse homeTeam,
  TeamResponse awayTeam,
  ScoreResponse score
) {}
