package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.competition.Competition;
import com.jeleniasty.betapp.features.competition.CompetitionRepository;
import com.jeleniasty.betapp.features.competition.CompetitionType;
import com.jeleniasty.betapp.features.team.Team;
import com.jeleniasty.betapp.features.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;
  private final TeamRepository teamRepository;
  private final CompetitionRepository competitionRepository;

  @Transactional
  public void saveMatch(SaveMatchDTO matchDTO) {
    var homeTeam = new Team("Kurwistan", "KRW", "flagag_ziuziuz.svg");
    var awayTeam = new Team("Republika Bongo", "BNG", "bububuu.png");

    teamRepository.save(homeTeam);
    teamRepository.save(awayTeam);

    var competition = new Competition(
      "Mistrzostwa zjebów",
      "ZJB",
      CompetitionType.CUP,
      2023
    );

    competitionRepository.save(competition);

    var match = new Match(
      matchDTO.status(),
      matchDTO.stage(),
      matchDTO.group(),
      matchDTO.homeOdds(),
      matchDTO.awayOdds(),
      matchDTO.utcDate()
    );
    match.assignCompetition(competition);
    match.assignAwayTeam(awayTeam);
    match.assignHomeTeam(homeTeam);
    matchRepository.save(match);
  }
}
