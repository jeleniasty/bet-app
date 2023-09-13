package com.jeleniasty.betapp.features.matchbet.repository;

import com.jeleniasty.betapp.BetappApplicationBaseTest;
import com.jeleniasty.betapp.features.matchbet.MatchBetNotFoundException;
import com.jeleniasty.betapp.features.matchbet.repository.entity.MatchBet;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class MatchBetRepositoryTest extends BetappApplicationBaseTest {

  @Autowired
  private MatchBetRepository matchBetRepository;

  @Test
  void shouldUpdateMatchBetById() {
    final int newHomeTeamScore = 1;
    final int newAwayTeamScore = 20;

    //given match bet
    var matchBetId = matchBetRepository
      .save(new MatchBet(0, 3, LocalDateTime.now(), 1L, 1L))
      .getId();

    //when update match bet by id
    matchBetRepository.updateMatchBetById(
      newHomeTeamScore,
      newAwayTeamScore,
      LocalDateTime.now(),
      matchBetId
    );

    //then home and away team scores should have been updated
    var updatedMatchBet = matchBetRepository.findById(matchBetId);

    Assertions
      .assertThat(
        updatedMatchBet
          .orElseThrow(() -> new MatchBetNotFoundException(matchBetId))
          .getHomeTeamScore()
      )
      .isEqualTo(newHomeTeamScore);

    Assertions
      .assertThat(
        updatedMatchBet
          .orElseThrow(() -> new MatchBetNotFoundException(matchBetId))
          .getAwayTeamScore()
      )
      .isEqualTo(newAwayTeamScore);
  }
}
