package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.httpclient.footballdata.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;

  public Team fetchOrSaveTeam(TeamDTO teamDTO) {
    var teamEntity = teamRepository
      .findByNameAndCode(teamDTO.name(), teamDTO.code())
      .orElseGet(() -> mapToEntity(teamDTO));

    this.teamRepository.save(teamEntity);
    return teamEntity;
  }

  public TeamDTO mapToDTO(TeamResponse teamResponse) {
    return new TeamDTO(
      null,
      teamResponse.name(),
      teamResponse.tla(),
      teamResponse.crest()
    );
  }

  private Team mapToEntity(TeamDTO teamDTO) {
    return new Team(teamDTO.name(), teamDTO.code(), teamDTO.flag());
  }
}
