package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.httpclient.footballdata.CompetitionMatchesResponse;
import com.jeleniasty.betapp.httpclient.footballdata.competition.CompetitionHttpClient;
import java.util.List;
import java.util.Set;
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
  public CompetitionDTO createOrUpdateCompetition(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    var competitionDTO = mapToDTO(
      competitionHttpClient.getCompetitionMatchesData(createCompetitonRequest)
    );

    return saveOrUpdateCompetition(competitionDTO);
  }

  private CompetitionDTO saveOrUpdateCompetition(
    CompetitionDTO competitionDTO
  ) {
    var competitionEntity = competitionRepository
      .findCompetitionByCodeAndSeason(
        competitionDTO.code(),
        competitionDTO.season()
      )
      .map(competition -> {
        competition.setStartDate(competitionDTO.startDate());
        competition.setEndDate(competitionDTO.endDate());

        competition.assignMatches(saveMatches(competitionDTO.matches()));

        return competition;
      })
      .orElseGet(() -> {
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

        return competition;
      });

    return mapToDTO(this.competitionRepository.save(competitionEntity));
  }

  private Set<Match> saveMatches(List<MatchDTO> matches) {
    return matches
      .stream()
      .filter(this::areTeamsAssigned)
      .map(this.matchService::saveOrUpdateMatch)
      .collect(Collectors.toSet());
  }

  //TODO add daily competition matches updates (i.a. for matches that had no teams assigned)

  private boolean areTeamsAssigned(MatchDTO matchDTO) {
    return (
      matchDTO.homeTeam().name() != null && matchDTO.awayTeam().name() != null
    );
  }

  private CompetitionDTO mapToDTO(
    CompetitionMatchesResponse competitionMatchesResponse
  ) {
    return new CompetitionDTO(
      null,
      competitionMatchesResponse.competition().name(),
      competitionMatchesResponse.competition().code(),
      competitionMatchesResponse.competition().type(),
      competitionMatchesResponse.filters().season(),
      competitionMatchesResponse.competition().emblem(),
      competitionMatchesResponse.resultSet().first(),
      competitionMatchesResponse.resultSet().last(),
      competitionMatchesResponse
        .matches()
        .stream()
        .map(this.matchService::mapToDTO)
        .toList()
    );
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
