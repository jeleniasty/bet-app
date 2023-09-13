package com.jeleniasty.betapp.features.match.repository;

import com.jeleniasty.betapp.BetappApplicationBaseTest;
import com.jeleniasty.betapp.features.match.MatchNotFoundException;
import com.jeleniasty.betapp.features.match.repository.entity.Match;
import com.jeleniasty.betapp.features.matchbet.MatchBetNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class MatchRepositoryTest extends BetappApplicationBaseTest {

  @Autowired
  MatchRepository matchRepository;

  @Test
  void shouldSetMatchResult() {
    final int newHomeTeamScore = 0;
    final int newAwayTeamScore = 33;

    //given match
    var matchId = matchRepository.save(new Match("POL", "KSA")).getId();

    //when set match result
    matchRepository.setMatchResult(newHomeTeamScore, newAwayTeamScore, matchId);
    var matchWithResult = matchRepository.findById(matchId);

    //then match result is set
    Assertions
      .assertThat(
        matchWithResult
          .orElseThrow(() -> new MatchNotFoundException(matchId))
          .getHomeTeamScore()
      )
      .isEqualTo(newHomeTeamScore);

    Assertions
      .assertThat(
        matchWithResult
          .orElseThrow(() -> new MatchBetNotFoundException(matchId))
          .getAwayTeamScore()
      )
      .isEqualTo(newAwayTeamScore);
  }
}
