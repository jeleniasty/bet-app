package com.jeleniasty.betapp.features.scheduler;

import com.jeleniasty.betapp.features.bet.SaveMatchResultDTO;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.ResultService;
import com.jeleniasty.betapp.httpclient.footballdata.MatchResponse;
import com.jeleniasty.betapp.httpclient.footballdata.match.MatchHttpClient;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchResultScheduler {

  private final SchedulerService schedulerService;
  private final MatchService matchService;
  private final ResultService resultService;
  private final MatchHttpClient matchHttpClient;

  @Value("${scheduler.duration.match-result-task}")
  private int duration = 5;

  @Scheduled(cron = "0 0 * * * *")
  @EventListener(ApplicationReadyEvent.class)
  public void setForTodayMatches() {
    var todaysMatches = this.matchService.fetchMatchesFromDate(Instant.now());

    var currentUtcDate = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

    todaysMatches
      .stream()
      .filter(match -> match.getDate().isAfter(currentUtcDate))
      .map(match ->
        new Task(
          match.getExternalId(),
          match.getDate().toInstant(ZoneOffset.UTC),
          Duration.ofMinutes(duration),
          () -> tryToSaveResult(match)
        )
      )
      .forEach(this.schedulerService::scheduleTask);
  }

  private void tryToSaveResult(Match match) {
    var matchExternalId = match.getExternalId();

    var matchExternalData = matchHttpClient.getMatchData(matchExternalId);

    if (isResultAvailable(matchExternalData)) {
      this.matchService.setMatchResult(
          new SaveMatchResultDTO(
            this.resultService.mapToDTO(matchExternalData.score()),
            match.getId(),
            match.getStatus()
          )
        );

      if (matchExternalData.status() == MatchStatus.FINISHED) {
        this.schedulerService.cancelScheduledTask(matchExternalId);
        log.info(
          "Result added. Cancelling task with id '" + matchExternalId + "'"
        );
      }
    }
  }

  private boolean isResultAvailable(MatchResponse matchResponse) {
    return matchResponse.score().winner() != null;
  }
}
