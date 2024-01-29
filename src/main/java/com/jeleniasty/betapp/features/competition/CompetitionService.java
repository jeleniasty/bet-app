package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.exceptions.CompetitionAlreadyExiststException;
import com.jeleniasty.betapp.features.exceptions.CompetitionNotFoundException;
import com.jeleniasty.betapp.features.exceptions.MatchNotFoundException;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.httpclient.footballdata.competition.CompetitionHttpClient;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionService {

  private final CompetitionRepository competitionRepository;
  private final CompetitionHttpClient competitionHttpClient;
  private final MatchService matchService;

  @Transactional
  public CompetitionDTO createNewCompetition(
    CompetitionRequest competitionRequest
  ) {
    return saveCompetition(
      competitionHttpClient.getCompetitionMatchesData(competitionRequest)
    );
  }

  @Transactional
  public List<CompetitionDTO> updateOngoingCompetitions() {
    var ongoingCompetitions =
      competitionRepository.findCompetitionByEndDateAfter(
        LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC)
      );

    var competitionDTOs = getCompetitionsData(ongoingCompetitions);
    return ongoingCompetitions
      .stream()
      .map(ongoingCompetition ->
        updateCompetition(
          ongoingCompetition,
          findCorrespondingDTO(ongoingCompetition, competitionDTOs)
        )
      )
      .filter(Optional::isPresent)
      .map(Optional::get)
      .toList();
  }

  private CompetitionDTO saveCompetition(CompetitionDTO competitionDTO) {
    if (
      competitionRepository.existsByCodeAndSeason(
        competitionDTO.code(),
        competitionDTO.season()
      )
    ) {
      CompetitionAlreadyExiststException.of(
        competitionDTO.code(),
        competitionDTO.season()
      );
    }

    var competition = new Competition(
      competitionDTO.name(),
      competitionDTO.code(),
      competitionDTO.type(),
      competitionDTO.season(),
      competitionDTO.emblem(),
      competitionDTO.startDate(),
      competitionDTO.endDate()
    );

    competition.assignMatches(saveMatches(competitionDTO.matches()));

    return mapToDTO(this.competitionRepository.save(competition));
  }

  private Set<Match> saveMatches(List<MatchDTO> matchDTOs) {
    return matchDTOs
      .stream()
      .map(this.matchService::saveMatch)
      .collect(Collectors.toSet());
  }

  private CompetitionDTO findCorrespondingDTO(
    Competition ongoingCompetition,
    List<CompetitionDTO> competitionDTOs
  ) {
    return competitionDTOs
      .stream()
      .filter(isCodeAndSeasonEqual(ongoingCompetition))
      .findFirst()
      .orElseThrow(() ->
        new CompetitionNotFoundException(
          ongoingCompetition.getCode(),
          ongoingCompetition.getSeason()
        )
      );
  }

  private Predicate<CompetitionDTO> isCodeAndSeasonEqual(
    Competition ongoingCompetition
  ) {
    return competitionDTO ->
      competitionDTO.code().equals(ongoingCompetition.getCode()) &&
      Objects.equals(competitionDTO.season(), ongoingCompetition.getSeason());
  }

  private Optional<CompetitionDTO> updateCompetition(
    Competition ongoingCompetition,
    CompetitionDTO competitionData
  ) {
    var competitionUpdated = false;
    if (!ongoingCompetition.getEndDate().equals(competitionData.endDate())) {
      ongoingCompetition.setEndDate(competitionData.endDate());
      competitionRepository.save(ongoingCompetition);
      competitionUpdated = true;
    }

    var updatedMatches = ongoingCompetition
      .getCompetitionMatches()
      .stream()
      .filter(areTeamsNotAssigned().or(isMatchInFuture().negate()))
      .map(match ->
        matchService.attemptToUpdate(
          match,
          findCorrespondingMatchDTO(match, competitionData.matches())
        )
      )
      .filter(Optional::isPresent)
      .map(Optional::get)
      .toList();

    if (competitionUpdated || !updatedMatches.isEmpty()) {
      return Optional.of(
        new CompetitionDTO(
          ongoingCompetition.getId(),
          ongoingCompetition.getName(),
          ongoingCompetition.getCode(),
          ongoingCompetition.getType(),
          ongoingCompetition.getSeason(),
          ongoingCompetition.getEmblem(),
          ongoingCompetition.getStartDate(),
          ongoingCompetition.getEndDate(),
          updatedMatches
        )
      );
    }

    return Optional.empty();
  }

  private Predicate<Match> areTeamsNotAssigned() {
    return match -> match.getHomeTeam() == null || match.getAwayTeam() == null;
  }

  private Predicate<Match> isMatchInFuture() {
    return match ->
      match.getStatus() == MatchStatus.FINISHED ||
      match.getStatus() == MatchStatus.AWARDED ||
      match.getStatus() == MatchStatus.IN_PLAY ||
      match.getStatus() == MatchStatus.PAUSED;
  }

  private MatchDTO findCorrespondingMatchDTO(
    Match match,
    List<MatchDTO> matchDTOs
  ) {
    return matchDTOs
      .stream()
      .filter(matchDTO ->
        Objects.equals(matchDTO.externalId(), match.getExternalId())
      )
      .findFirst()
      .orElseThrow(() ->
        MatchNotFoundException.withExternalId(match.getExternalId())
      );
  }

  private List<CompetitionDTO> getCompetitionsData(
    List<Competition> ongoingCompetitions
  ) {
    return ongoingCompetitions
      .stream()
      .map(competition ->
        new CompetitionRequest(competition.getCode(), competition.getSeason())
      )
      .map(competitionHttpClient::getCompetitionMatchesData)
      .toList();
  }

  private CompetitionDTO mapToDTO(Competition competition) {
    return new CompetitionDTO(
      competition.getId(),
      competition.getName(),
      competition.getCode(),
      competition.getType(),
      competition.getSeason(),
      competition.getEmblem(),
      competition.getStartDate(),
      competition.getEndDate(),
      competition
        .getCompetitionMatches()
        .stream()
        .map(this.matchService::mapToDTO)
        .toList()
    );
  }
}
