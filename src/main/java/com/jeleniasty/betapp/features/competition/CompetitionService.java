package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.httpclient.match.CompetitionMatchesResponse;
import com.jeleniasty.betapp.httpclient.match.MatchesHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionService {

  private final CompetitionRepository competitionRepository;
  private final MatchesHttpClient matchesHttpClient;
  private final MatchService matchService;

  @Transactional
  public void createNewCompetition(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    //get competition data from api
    var competitionData = matchesHttpClient.getCompetitionMatches(
      createCompetitonRequest
    );

    //save or fetch competition
    var savedCompetition = fetchOrSaveCompetition(mapToDTO(competitionData));

    //save matches
    competitionData
      .getMatches()
      .stream()
      .filter(matchResponse ->
        matchResponse.getAwayTeam().getName() != null &&
        matchResponse.getHomeTeam().getName() != null
      )
      .forEach(matchResponse ->
        this.matchService.fetchOrSaveMatch(matchResponse, savedCompetition)
      );
  }

  private CompetitionDTO mapToDTO(
    CompetitionMatchesResponse competitionMatchesResponse
  ) {
    var competition = competitionMatchesResponse.getCompetition();
    return new CompetitionDTO(
      null,
      competition.getName(),
      competition.getCode(),
      competition.getType(),
      competitionMatchesResponse.getFilters().getSeason()
    );
  }

  private Competition fetchOrSaveCompetition(CompetitionDTO competitionDTO) {
    return competitionRepository
      .findCompetitionByCodeAndSeason(
        competitionDTO.code(),
        competitionDTO.season()
      )
      .orElseGet(() -> {
        var competition = new Competition(
          competitionDTO.name(),
          competitionDTO.code(),
          competitionDTO.type(),
          competitionDTO.season()
        );
        this.competitionRepository.save(competition);
        return competition;
      });
  }
}
