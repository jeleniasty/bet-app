package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.competition.CompetitionDTO;
import com.jeleniasty.betapp.features.team.TeamDTO;
import java.time.LocalDateTime;

public record MatchDTO(
  Long id,
  TeamDTO homeTeam,
  Float homeOdds,
  TeamDTO awayTeam,
  Float awayOdds,
  MatchStatus status,
  CompetitionStage stage,
  Character group,
  CompetitionDTO competition,
  LocalDateTime matchDate
) {}
