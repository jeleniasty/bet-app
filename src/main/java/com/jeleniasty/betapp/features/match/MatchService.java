package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.SaveMatchResultDTO;
import com.jeleniasty.betapp.features.exceptions.MatchNotFoundException;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.dto.UpcomingMatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.odds.MatchOddsDTO;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.team.TeamDTO;
import com.jeleniasty.betapp.features.team.TeamService;
import com.jeleniasty.betapp.httpclient.footballdata.MatchResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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
      this.matchRepository.findByHomeTeamNameContainingAndAwayTeamNameContainingAndDate(
          homeTeam.name(),
          awayTeam.name(),
          matchDTO.date()
        )
        .map(match -> {
          match.setDate(matchDTO.date());

          return match;
        })
        .orElseGet(() ->
          new Match(
            matchDTO.status(),
            matchDTO.stage(),
            matchDTO.group(),
            1.00f,
            1.00f,
            1.00f,
            matchDTO.date(),
            matchDTO.externalId()
          )
        );

    if (isMatchCompleted(matchDTO)) {
      matchToSave.setResult(resultService.saveResult(matchDTO.resultDTO()));
    }

    if (
      areTeamsAssigned(homeTeam, awayTeam) && !areTeamsAlreadySaved(matchToSave)
    ) {
      matchToSave.setHomeTeam(
        this.teamService.fetchOrSaveTeam(
            new TeamDTO(null, homeTeam.name(), homeTeam.code(), homeTeam.flag())
          )
      );
      matchToSave.setAwayTeam(
        this.teamService.fetchOrSaveTeam(
            new TeamDTO(null, awayTeam.name(), awayTeam.code(), awayTeam.flag())
          )
      );
    }
    return matchToSave;
  }

  private static boolean isMatchCompleted(MatchDTO matchDTO) {
    return matchDTO.resultDTO().winner() != null;
  }

  public Match findMatch(Long matchId) {
    return matchRepository
      .findById(matchId)
      .orElseThrow(() -> new MatchNotFoundException(matchId));
  }

  public Match findMatchByExternalId(Long externalId) {
    return matchRepository
      .findByExternalId(externalId)
      .orElseThrow(() -> new MatchNotFoundException(externalId));
  }

  public List<Match> findMatches(Instant utcDate) {
    return this.matchRepository.findAllByDateBetween(
        getDateWithGivenTime(utcDate, LocalTime.MIN),
        getDateWithGivenTime(utcDate, LocalTime.MAX)
      );
  }

  @Transactional
  public void setMatchResult(SaveMatchResultDTO saveMatchResultDTO) {
    var matchToBeUpdated = findMatchByExternalId(saveMatchResultDTO.matchId());

    matchToBeUpdated.setStatus(saveMatchResultDTO.status());
    matchToBeUpdated.setResult(
      resultService.saveResult(saveMatchResultDTO.resultDTO())
    );
    if (saveMatchResultDTO.status() == MatchStatus.FINISHED) {
      eventPublisher.publishEvent(
        new MatchCompletionEvent(matchToBeUpdated.getId())
      );
    }
  }

  public void setMatchOdds(MatchOddsDTO odd) {
    var homeTeam = this.teamService.findTeam(odd.homeTeamName());
    var awayTeam = this.teamService.findTeam(odd.awayTeamName());

    var match =
      this.matchRepository.findByHomeTeamNameContainingAndAwayTeamNameContainingAndDate(
          homeTeam.getName(),
          awayTeam.getName(),
          odd.date()
        );
    if (match.isEmpty()) {
      log.error(
        "Match not found: " +
        homeTeam.getName() +
        " vs " +
        awayTeam.getName() +
        " timed for " +
        odd.date()
      );
      return;
    }
    match.get().setHomeOdds(odd.homeOdds());
    match.get().setAwayOdds(odd.awayOdds());
    match.get().setDrawOdds(odd.drawOdds());
    this.matchRepository.save(match.get());
  }

  //TODO add functionality to handle match date inconsistencies

  public List<UpcomingMatchDTO> getUpcomingMatches() {
    return matchRepository.findTop10ByStatusOrderByDate();
  }

  public MatchDTO getUpcomingMatch(Long id) {
    var match = matchRepository
      .findById(id)
      .orElseThrow(() -> new MatchNotFoundException(id));

    return mapToDTO(match);
  }

  public MatchDTO mapToDTO(MatchResponse matchResponse) {
    return new MatchDTO(
      null,
      this.teamService.mapToDTO(matchResponse.homeTeam()),
      this.teamService.mapToDTO(matchResponse.awayTeam()),
      1f,
      1f,
      1f,
      matchResponse.status(),
      matchResponse.stage(),
      matchResponse.group(),
      matchResponse.utcDate(),
      this.resultService.mapToDTO(matchResponse.score()),
      matchResponse.id()
    );
  }

  public MatchDTO mapToDTO(Match match) {
    return new MatchDTO(
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
      match.getDate(),
      this.resultService.mapToDTO(match.getResult()),
      match.getExternalId()
    );
  }

  private boolean areTeamsAssigned(TeamDTO homeTeam, TeamDTO awayTeam) {
    return homeTeam.name() != null && awayTeam.name() != null;
  }

  private boolean areTeamsAlreadySaved(Match matchToSave) {
    return (
      matchToSave.getHomeTeam() != null && matchToSave.getAwayTeam() != null
    );
  }

  private LocalDateTime getDateWithGivenTime(Instant utcDate, LocalTime time) {
    return LocalDateTime.ofInstant(utcDate, ZoneOffset.UTC).with(time);
  }
}
