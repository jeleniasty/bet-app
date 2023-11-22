package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.match.MatchDTO;
import java.util.List;

public record CompetitionDTO(
  Long id,
  String name,
  String code,
  CompetitionType type,
  Integer season,
  List<MatchDTO> matchDTOs
) {}
