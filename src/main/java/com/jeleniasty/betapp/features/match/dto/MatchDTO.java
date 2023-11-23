package com.jeleniasty.betapp.features.match.dto;

import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.Group;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.team.TeamDTO;
import java.time.LocalDateTime;

public record MatchDTO(
  Long id,
  TeamDTO homeTeam,
  TeamDTO awayTeam,
  Float homeOdds,
  Float awayOdds,
  Float drawOdds,
  MatchStatus status,
  CompetitionStage stage,
  Group group,
  LocalDateTime date,
  Long externalId
) {}
