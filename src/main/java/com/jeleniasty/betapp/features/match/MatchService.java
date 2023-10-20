package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.competition.CompetitionService;
import com.jeleniasty.betapp.features.exceptions.MatchNotFoundException;
import com.jeleniasty.betapp.features.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;
  private final TeamService teamService;
  private final CompetitionService competitionService;

  @Transactional
  public void saveMatch(SaveMatchDTO matchDTO) {
    var competition = competitionService.fetchCompetition(
      matchDTO.competitionId()
    );

    var homeTeam = teamService.fetchTeam(matchDTO.homeTeamId());
    var awayTeam = teamService.fetchTeam(matchDTO.awayTeamId());

    var newMatch = new Match(
      matchDTO.status(),
      matchDTO.stage(),
      matchDTO.group(),
      matchDTO.homeOdds(),
      matchDTO.awayOdds(),
      matchDTO.utcDate()
    );
    newMatch.assignCompetition(competition);
    newMatch.assignAwayTeam(awayTeam);
    newMatch.assignHomeTeam(homeTeam);

    matchRepository.save(newMatch);
  }

  public Match fetchMatch(Long matchId) {
    return matchRepository
      .findById(matchId)
      .orElseThrow(() -> new MatchNotFoundException(matchId));
  }
}
