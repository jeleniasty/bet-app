package com.jeleniasty.betapp.features.competition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.httpclient.footballdata.CompetitionMatchesDeserializer;
import java.time.LocalDate;
import java.util.List;

@JsonDeserialize(using = CompetitionMatchesDeserializer.class)
public record CompetitionDTO(
  Long id,
  String name,
  String code,
  CompetitionType type,
  Integer season,
  String emblem,
  LocalDate startDate,
  LocalDate endDate,
  List<MatchDTO> matches
) {}
