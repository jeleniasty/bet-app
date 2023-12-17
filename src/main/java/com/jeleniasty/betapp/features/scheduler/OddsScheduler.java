package com.jeleniasty.betapp.features.scheduler;

import com.jeleniasty.betapp.config.BetAppProperties;
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
  private final BetAppProperties betAppProperties;
  private final MatchService matchService;

  @Scheduled(cron = "*/30 * * * * *")
  public void setOdds() {
    var availableOdds = collectAvailableOdds(
      betAppProperties.getTheOddsApi().getCompetitionKey()
    );

    availableOdds.forEach(this.matchService::setMatchOdds);
  }

  //TODO more frequent scheduler added dynamically

  public List<MatchOddsDTO> collectAvailableOdds(
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
