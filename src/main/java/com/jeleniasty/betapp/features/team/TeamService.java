package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.features.exceptions.TeamNotFoundException;
import com.jeleniasty.betapp.httpclient.footballdata.TeamResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final TeamNamesDictionary teamNamesDictionary;

  public Team fetchOrSaveTeam(TeamDTO teamDTO) {
    var teamEntity = teamRepository
      .findByNameContains(teamDTO.name())
      .orElseGet(() -> mapToEntity(teamDTO));

    this.teamRepository.save(teamEntity);
    return teamEntity;
  }

  public Team findTeam(String teamName) {
    return this.teamRepository.findByNameContains(teamName)
      .orElseGet(() -> findTeamInDictionary(teamName));
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

  private Team findTeamInDictionary(String teamName) {
    return getTeamNamesFromDictionary(teamName)
      .stream()
      .map(teamRepository::findByNameContains)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .findFirst()
      .orElseThrow(() -> new TeamNotFoundException(teamName));
  }

  //TODO update spreadsheet with new team name variation if none is found in existing dictionary

  private List<String> getTeamNamesFromDictionary(String teamName) {
    return this.teamNamesDictionary.getTeamNamesDictionary().get(teamName);
  }

  private Team mapToEntity(TeamDTO teamDTO) {
    return new Team(teamDTO.name(), teamDTO.code(), teamDTO.flag());
  }
}
