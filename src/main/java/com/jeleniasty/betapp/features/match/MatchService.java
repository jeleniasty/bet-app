package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.match.repository.MatchRepository;
import com.jeleniasty.betapp.features.match.repository.entity.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;

  public void saveMatch(Match match) {
    matchRepository.save(
      new Match(match.getHomeTeamCode(), match.getAwayTeamCode())
    );
  }

  public void deleteMatch(Long id) {
    matchRepository.deleteById(id);
  }

  public Optional<Match> getMatch(Long id) {
    return matchRepository.findById(id);
  }

  public Iterable<Match> getMatches() {
    return matchRepository.findAll();
  }

  public void saveMatchResult(Match match) {
    matchRepository.setMatchResult(
      match.getHomeTeamScore(),
      match.getAwayTeamScore(),
      match.getId()
    );
  }
}
