package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.exceptions.CompetitionAlreadyExiststException;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
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
  public CompetitionDTO createNewCompetition(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    return saveCompetition(
      competitionHttpClient.getCompetitionMatchesData(createCompetitonRequest)
    );
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

  //TODO refactor this method to save all matches and then update:
  //skip finished matches
  //skip matches in play
  //try to update match date and status when saved match has status scheduled (it means match date time is not specified)
  //try to update teams and status when saved match has no teams
  //TODO add daily competition matches updates (i.a. for matches that had no teams assigned)

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
