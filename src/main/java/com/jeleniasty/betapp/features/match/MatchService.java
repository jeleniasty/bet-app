package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.matchresult.MatchResult;
import com.jeleniasty.betapp.features.matchresult.MatchResultRepository;
import com.jeleniasty.betapp.features.matchresult.SaveMatchResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;
  private final MatchResultRepository matchResultRepository;

  @Transactional
  public void createMatch(SaveMatchDTO matchDTO) {
    matchRepository.save(
      new Match(
        matchDTO.homeTeamCode(),
        matchDTO.awayTeamCode(),
        matchDTO.startTime(),
        matchDTO.stadium_id()
      )
    );
  }

  @Transactional
  public void addMatchResultDTO(SaveMatchResultDTO matchResultDTO) {
    matchResultRepository.save(
      new MatchResult(
        matchResultDTO.homeTeamScore(),
        matchResultDTO.awayTeamScore(),
        matchResultDTO.duration()
      )
    );
  }
}
