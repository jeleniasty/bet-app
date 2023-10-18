package com.jeleniasty.betapp.features.match;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;

  @Transactional
  public void saveMatch(SaveMatchDTO matchDTO) {
    matchRepository.save(
      new Match(
        matchDTO.status(),
        matchDTO.stage(),
        matchDTO.group(),
        matchDTO.homeOdds(),
        matchDTO.awayOdds(),
        matchDTO.utcDate(),
        matchDTO.competition(),
        matchDTO.homeTeam(),
        matchDTO.awayTeam()
      )
    );
  }
}
