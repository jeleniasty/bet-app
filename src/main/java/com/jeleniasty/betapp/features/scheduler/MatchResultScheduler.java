package com.jeleniasty.betapp.features.scheduler;

import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.scheduler.task.MatchResultTask;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchResultScheduler {

  private final SchedulerService schedulerService;
  private final MatchService matchService;
  private final MatchResultTask matchResultTask;

  @Value("${scheduler.duration.match-result-task}")
  private int duration;

  @Scheduled(cron = "0 0 * * * *")
  public void setForTodaysMatches() {
    var todaysMatches = this.matchService.fetchMatchesFromDate(Instant.now());

    todaysMatches
      .stream()
      .map(match ->
        new Task(
          match.getExternalId(),
          match.getDate().toInstant(ZoneOffset.UTC),
          Duration.ofMinutes(duration),
          this.matchResultTask
        )
      )
      .forEach(this.schedulerService::scheduleTask);
  }
}
