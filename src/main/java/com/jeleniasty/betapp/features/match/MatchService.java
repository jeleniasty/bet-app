package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.bet.SaveMatchResultDTO;
import com.jeleniasty.betapp.features.exceptions.MatchNotFoundException;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.dto.UpcomingMatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.odds.MatchOddsDTO;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.features.team.TeamService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
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

  public Match saveMatch(MatchDTO matchDTO) {
    return matchRepository
      .findByExternalId(matchDTO.externalId())
      .orElseGet(() -> {
        var newMatch = new Match(
          matchDTO.status(),
          matchDTO.stage(),
          matchDTO.group(),
          matchDTO.homeOdds(),
          matchDTO.awayOdds(),
          matchDTO.drawOdds(),
          matchDTO.date(),
          matchDTO.externalId()
        );
        if (matchDTO.homeTeam() != null) {
          newMatch.setHomeTeam(teamService.getTeam(matchDTO.homeTeam()));
        }

        if (matchDTO.awayTeam() != null) {
          newMatch.setAwayTeam(teamService.getTeam(matchDTO.awayTeam()));
        }

        if (matchDTO.result() != null) {
          newMatch.setResult(resultService.saveResult(matchDTO.result()));
        }
        return newMatch;
      });
  }

  public Optional<MatchDTO> attemptToUpdate(
    Match matchEntity,
    MatchDTO matchData
  ) {
    var statusUpdated = false;
    var teamsUpdated = false;

    if (matchEntity.getStatus() != matchData.status()) {
      matchEntity.setStatus(matchData.status());
      matchEntity.setDate(matchData.date());
      statusUpdated = true;
    }
    if (!areTeamsAssigned(matchEntity) && areTeamsAssigned(matchData)) {
      matchEntity.setHomeTeam(this.teamService.getTeam(matchData.homeTeam()));
      matchEntity.setAwayTeam(this.teamService.getTeam(matchData.awayTeam()));
      teamsUpdated = true;
    }
    if (statusUpdated || teamsUpdated) {
      return Optional.of(mapToDTO(matchRepository.save(matchEntity)));
    }
    return Optional.empty();
  }

  public Match findMatch(Long matchId) {
    return matchRepository
      .findById(matchId)
      .orElseThrow(() -> MatchNotFoundException.withId(matchId));
  }

  public Match findMatchByExternalId(Long externalId) {
    return matchRepository
      .findByExternalId(externalId)
      .orElseThrow(() -> MatchNotFoundException.withExternalId(externalId));
  }

  public List<Match> findMatches(Instant utcDate) {
    return this.matchRepository.findAllByDateBetween(
        getDateWithGivenTime(utcDate, LocalTime.MIN),
        getDateWithGivenTime(utcDate, LocalTime.MAX)
      );
  }

  @Transactional
  public void setMatchResult(SaveMatchResultDTO saveMatchResultDTO) {
    var matchToBeUpdated = findMatchByExternalId(
      saveMatchResultDTO.externalId()
    );

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

  public MatchDTO getMatch(Long id) {
    var match = matchRepository
      .findById(id)
      .orElseThrow(() -> MatchNotFoundException.withId(id));

    return mapToDTO(match);
  }

  public MatchDTO mapToDTO(Match match) {
    return new MatchDTO(
      match.getId(),
      this.teamService.mapToDTO(match.getHomeTeam()).orElse(null),
      this.teamService.mapToDTO(match.getAwayTeam()).orElse(null),
      match.getHomeOdds(),
      match.getAwayOdds(),
      match.getDrawOdds(),
      match.getStatus(),
      match.getStage(),
      match.getGroup(),
      match.getDate(),
      this.resultService.mapToDTO(match.getResult()).orElse(null),
      match.getExternalId()
    );
  }

  private boolean areTeamsAssigned(MatchDTO matchDTO) {
    return matchDTO.homeTeam() != null || matchDTO.awayTeam() != null;
  }

  private boolean areTeamsAssigned(Match match) {
    return match.getHomeTeam() != null || match.getAwayTeam() != null;
  }

  private LocalDateTime getDateWithGivenTime(Instant utcDate, LocalTime time) {
    return LocalDateTime.ofInstant(utcDate, ZoneOffset.UTC).with(time);
  }
}
