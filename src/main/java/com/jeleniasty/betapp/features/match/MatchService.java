package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.MatchResultDTO;
import com.jeleniasty.betapp.features.competition.CompetitionDTO;
import com.jeleniasty.betapp.features.competition.CompetitionService;
import com.jeleniasty.betapp.features.exceptions.MatchNotFoundException;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.team.TeamDTO;
import com.jeleniasty.betapp.features.team.TeamService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;
  private final TeamService teamService;
  private final CompetitionService competitionService;
  private final ResultService resultService;
  private final ApplicationEventPublisher eventPublisher;

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
  }

  public Match fetchMatch(Long matchId) {
    return matchRepository
      .findById(matchId)
      .orElseThrow(() -> new MatchNotFoundException(matchId));
  }

  @Transactional
  public void setMatchResult(MatchResultDTO matchResultDTO) {
    var result = resultService.saveResult(matchResultDTO.saveResultDTO());
    var matchToBeUpdated = fetchMatch(matchResultDTO.matchId());

    matchToBeUpdated.setResult(result);
    eventPublisher.publishEvent(
      new MatchResultSetEvent(matchToBeUpdated.getId())
    );
  }

  public List<UpcomingMatchDTO> getUpcomingMatches() {
    return matchRepository.findTop10ByStatusOrderByDate();
  }

  public MatchDTO getUpcomingMatch(Long id) {
    var match = matchRepository
      .findById(id)
      .orElseThrow(() -> new MatchNotFoundException(id));

    return new MatchDTO(
      match.getId(),
      new TeamDTO(
        match.getHomeTeam().getId(),
        match.getHomeTeam().getName(),
        match.getHomeTeam().getCode(),
        match.getHomeTeam().getFlag()
      ),
      match.getHomeOdds(),
      new TeamDTO(
        match.getAwayTeam().getId(),
        match.getAwayTeam().getName(),
        match.getAwayTeam().getCode(),
        match.getAwayTeam().getFlag()
      ),
      match.getAwayOdds(),
      match.getStatus(),
      match.getStage(),
      match.getGroup(),
      new CompetitionDTO(
        match.getCompetition().getId(),
        match.getCompetition().getName(),
        match.getCompetition().getCode(),
        match.getCompetition().getType(),
        match.getCompetition().getSeason()
      ),
      match.getDate()
    );
  }
}
