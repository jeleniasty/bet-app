package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.SaveMatchResultDTO;
import com.jeleniasty.betapp.features.competition.Competition;
import com.jeleniasty.betapp.features.competition.CompetitionDTO;
import com.jeleniasty.betapp.features.exceptions.MatchNotFoundException;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.team.TeamDTO;
import com.jeleniasty.betapp.features.team.TeamService;
import com.jeleniasty.betapp.httpclient.match.CompetitionMatchesResponse;
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
  private final ResultService resultService;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public void fetchOrSaveMatch(
    CompetitionMatchesResponse.MatchResponse matchResponse,
    Competition competition
  ) {
    var homeTeam = matchResponse.getHomeTeam();
    var awayTeam = matchResponse.getAwayTeam();

    this.matchRepository.findByHomeTeamNameAndAwayTeamNameAndDate(
        homeTeam.getName(),
        awayTeam.getName(),
        matchResponse.getUtcDate()
      )
      .orElseGet(() -> {
        var match = new Match(
          matchResponse.getStatus(),
          matchResponse.getStage(),
          matchResponse.getGroup(),
          2.11f,
          1.23f,
          1.45f,
          matchResponse.getUtcDate()
        );

        //TODO change mocked odds with real fetching odds from external api

        match.assignCompetition(competition);

        if (homeTeam.getTla() == null && awayTeam.getTla() == null) {
          return match;
        }

        match.assignHomeTeam(
          this.teamService.fetchOrSaveTeam(
              new TeamDTO(
                null,
                homeTeam.getName(),
                homeTeam.getTla(),
                homeTeam.getCrest()
              )
            )
        );
        match.assignAwayTeam(
          this.teamService.fetchOrSaveTeam(
              new TeamDTO(
                null,
                awayTeam.getName(),
                awayTeam.getTla(),
                awayTeam.getCrest()
              )
            )
        );

        return match;
      });
  }

  public Match fetchMatch(Long matchId) {
    return matchRepository
      .findById(matchId)
      .orElseThrow(() -> new MatchNotFoundException(matchId));
  }

  @Transactional
  public void setMatchResult(SaveMatchResultDTO saveMatchResultDTO) {
    var result = resultService.saveResult(saveMatchResultDTO.matchResultDTO());
    var matchToBeUpdated = fetchMatch(saveMatchResultDTO.matchId());

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
