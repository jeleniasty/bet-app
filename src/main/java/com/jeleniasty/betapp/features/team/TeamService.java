package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.httpclient.footballdata.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final TeamNamesDictionary teamNamesDictionary;

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

  public void synchroniseDictionary() {
    this.teamNamesDictionary.updateDictionary();
  }

  private Team mapToEntity(TeamDTO teamDTO) {
    return new Team(teamDTO.name(), teamDTO.code(), teamDTO.flag());
  }
}
