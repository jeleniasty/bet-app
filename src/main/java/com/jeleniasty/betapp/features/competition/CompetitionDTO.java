package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import java.time.LocalDate;
import java.util.List;

public record CompetitionDTO(
  Long id,
  String name,
  String code,
  CompetitionType type,
  Integer season,
  String emblem,
  LocalDate startDate,
  LocalDate endDate,
  List<MatchDTO> matchDTOs
) {}
