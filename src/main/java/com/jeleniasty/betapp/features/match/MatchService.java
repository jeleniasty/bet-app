package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.SaveMatchResultDTO;
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
  public Match saveOrUpdateMatch(MatchDTO matchDTO) {
    var homeTeam = matchDTO.homeTeam();
    var awayTeam = matchDTO.awayTeam();

    var matchToSave =
      this.matchRepository.findByHomeTeamNameAndAwayTeamNameAndDate(
          homeTeam.name(),
          awayTeam.name(),
          matchDTO.matchDate()
        )
        .map(match -> {
          match.setStatus(matchDTO.status());
          match.setStage(matchDTO.stage());
          match.setGroup(matchDTO.group());
          match.setHomeOdds(matchDTO.homeOdds());
          match.setAwayOdds(matchDTO.awayOdds());
          match.setDrawOdds(matchDTO.drawOdds());
          match.setDate(matchDTO.matchDate());

          return match;
        })
        .orElseGet(() -> {
          return new Match(
            matchDTO.status(),
            matchDTO.stage(),
            matchDTO.group(),
            2.11f,
            1.23f,
            1.45f,
            matchDTO.matchDate()
          );
          //TODO change mocked odds with real fetching odds from external API
        });

    if (areTeamsAssigned(homeTeam, awayTeam)) {
      matchToSave.assignHomeTeam(
        this.teamService.fetchOrSaveTeam(
            new TeamDTO(null, homeTeam.name(), homeTeam.code(), homeTeam.flag())
          )
      );
      matchToSave.assignAwayTeam(
        this.teamService.fetchOrSaveTeam(
            new TeamDTO(null, awayTeam.name(), awayTeam.code(), awayTeam.flag())
          )
      );
    }
    return matchToSave;
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

  public CompetitionDTO getUpcomingMatch(Long id) {
    var match = matchRepository
      .findById(id)
      .orElseThrow(() -> new MatchNotFoundException(id));

    return mapToDTO(match);
  }

  private boolean areTeamsAssigned(TeamDTO homeTeam, TeamDTO awayTeam) {
    return homeTeam.name() != null && awayTeam.name() != null;
  }

  public MatchDTO mapToDTO(
    CompetitionMatchesResponse.MatchResponse matchResponse
  ) {
    return new MatchDTO(
      null,
      this.teamService.mapToDTO(matchResponse.getHomeTeam()),
      this.teamService.mapToDTO(matchResponse.getAwayTeam()),
      1f,
      2f,
      3f,
      matchResponse.getStatus(),
      matchResponse.getStage(),
      matchResponse.getGroup(),
      matchResponse.getUtcDate()
    );
  }

  public CompetitionDTO mapToDTO(Match match) {
    return new CompetitionDTO(
      match.getCompetition().getId(),
      match.getCompetition().getName(),
      match.getCompetition().getCode(),
      match.getCompetition().getType(),
      match.getCompetition().getSeason(),
      List.of(
        new MatchDTO(
          match.getId(),
          new TeamDTO(
            match.getHomeTeam().getId(),
            match.getHomeTeam().getName(),
            match.getHomeTeam().getCode(),
            match.getHomeTeam().getFlag()
          ),
          new TeamDTO(
            match.getAwayTeam().getId(),
            match.getAwayTeam().getName(),
            match.getAwayTeam().getCode(),
            match.getAwayTeam().getFlag()
          ),
          match.getHomeOdds(),
          match.getAwayOdds(),
          match.getDrawOdds(),
          match.getStatus(),
          match.getStage(),
          match.getGroup(),
          match.getDate()
        )
      )
    );
  }
}
