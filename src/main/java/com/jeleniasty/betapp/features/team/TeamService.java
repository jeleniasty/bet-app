package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.features.exceptions.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;

  public Team fetchTeam(long teamId) {
    return teamRepository
      .findById(teamId)
      .orElseThrow(() -> new TeamNotFoundException(teamId));
  }
}
