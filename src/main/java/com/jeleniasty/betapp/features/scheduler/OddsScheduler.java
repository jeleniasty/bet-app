package com.jeleniasty.betapp.features.scheduler;

import com.jeleniasty.betapp.config.OddsApiProperties;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.odds.MatchOddsDTO;
import com.jeleniasty.betapp.features.odds.OddsService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OddsScheduler {

  private final OddsService oddsService;
  private final OddsApiProperties oddsApiProperties;
  private final MatchService matchService;

  @Scheduled(cron = "${scheduler.duration.odds-task-cron}")
  public void setOdds() {
    var competitionKeys = oddsApiProperties.getCompetitionKey();

    collectAvailableOdds(competitionKeys)
      .forEach(this.matchService::setMatchOdds);
    log.info("Available odds updated");
  }

  private List<MatchOddsDTO> collectAvailableOdds(
    Map<String, String> competitionKeys
  ) {
    return competitionKeys
      .values()
      .stream()
      .map(this.oddsService::getCompetitionOdds)
      .flatMap(Collection::stream)
      .toList();
  }
}
