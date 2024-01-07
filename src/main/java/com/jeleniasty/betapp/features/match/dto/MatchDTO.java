package com.jeleniasty.betapp.features.match.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.Group;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.ResultDTO;
import com.jeleniasty.betapp.features.team.TeamDTO;
import com.jeleniasty.betapp.httpclient.footballdata.match.MatchDeserializer;
import java.time.LocalDateTime;

@JsonDeserialize(using = MatchDeserializer.class)
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
  ResultDTO result,
  Long externalId
) {}
