package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.features.exceptions.TeamNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final TeamNamesDictionary teamNamesDictionary;

  public Team getTeam(TeamDTO teamDTO) {
    var teamEntity = teamRepository
      .findByNameContains(teamDTO.name())
      .orElseGet(() -> mapToEntity(teamDTO));

    return this.teamRepository.save(teamEntity);
  }

  public Team findTeam(String teamName) {
    return this.teamRepository.findByNameContains(teamName)
      .orElseGet(() -> findTeamInDictionary(teamName));
  }

  public Optional<TeamDTO> mapToDTO(Team team) {
    if (team == null) {
      return Optional.empty();
    }
    return Optional.of(
      new TeamDTO(team.getId(), team.getName(), team.getCode(), team.getFlag())
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
