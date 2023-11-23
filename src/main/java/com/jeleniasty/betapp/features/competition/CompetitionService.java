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
  public void createNewCompetition(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    var competitionDTO = mapToDTO(
      competitionHttpClient.getCompetitionMatchesData(createCompetitonRequest)
    );

    saveOrUpdateCompetition(competitionDTO);
  }

  private void saveOrUpdateCompetition(CompetitionDTO competitionDTO) {
    var competitionEntity = competitionRepository
      .findCompetitionByCodeAndSeason(
        competitionDTO.code(),
        competitionDTO.season()
      )
      .map(competition -> {
        competition.setName(competitionDTO.name());
        competition.setCode(competitionDTO.code());
        competition.setType(competitionDTO.type());
        competition.setSeason(competitionDTO.season());
        competition.setEmblem(competitionDTO.emblem());
        competition.setStartDate(competitionDTO.startDate());
        competition.setEndDate(competitionDTO.endDate());

        competition.assignMatches(
          saveCompetitionMatches(competitionDTO.matchDTOs())
        );

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

        competition.assignMatches(
          saveCompetitionMatches(competitionDTO.matchDTOs())
        );

        return competition;
      });

    this.competitionRepository.save(competitionEntity);
  }

  private Set<Match> saveCompetitionMatches(List<MatchDTO> matches) {
    return matches
      .stream()
      .filter(this::areTeamsAssigned)
      .map(this.matchService::saveOrUpdateMatch)
      .collect(Collectors.toSet());
  }

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
}
