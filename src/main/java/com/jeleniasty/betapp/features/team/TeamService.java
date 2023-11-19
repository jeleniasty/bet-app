package com.jeleniasty.betapp.features.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;

  public Team fetchOrSaveTeam(TeamDTO teamDTO) {
    return teamRepository
      .findByNameAndCode(teamDTO.name(), teamDTO.code())
      .orElseGet(() -> this.teamRepository.save(mapToEntity(teamDTO)));
  }

  private Team mapToEntity(TeamDTO teamDTO) {
    return new Team(teamDTO.name(), teamDTO.code(), teamDTO.flag());
  }
}
