package com.jeleniasty.betapp.features.scheduler;

import com.jeleniasty.betapp.features.bet.SaveMatchResultDTO;
import com.jeleniasty.betapp.features.match.MatchService;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.httpclient.footballdata.match.MatchHttpClient;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final MatchHttpClient matchHttpClient;

  private final Queue<Long> ongoingMatches = new LinkedList<>();

  @Scheduled(cron = "${scheduler.duration.match-result-task-cron}")
  @EventListener(ApplicationReadyEvent.class)
  public void setTasksForTodayMatches() {
    var todaysMatches = this.matchService.findMatches(Instant.now());

    todaysMatches
      .stream()
      .filter(isMatchNotCompleted())
      .map(match ->
        new DelayedTask(
          match.getExternalId(),
          match.getDate().toInstant(ZoneOffset.UTC),
          () -> this.ongoingMatches.add(match.getExternalId())
        )
      )
      .forEach(this.schedulerService::scheduleTaskExecution);
  }

  @Scheduled(
    fixedDelayString = "${scheduler.duration.match-result-update-millis}"
  )
  public void updateMatchResults() {
    if (!this.ongoingMatches.isEmpty()) {
      this.ongoingMatches.forEach(this::tryToSaveResult);
    }
  }

  private void tryToSaveResult(Long matchExternalId) {
    var matchExternalData = matchHttpClient.getMatchData(matchExternalId);
    if (isResultAvailable(matchExternalData)) {
      this.matchService.setMatchResult(
          new SaveMatchResultDTO(
            matchExternalData.result(),
            null,
            matchExternalId,
            matchExternalData.status()
          )
        );

      if (matchExternalData.status() == MatchStatus.FINISHED) {
        this.ongoingMatches.remove();
      } else {
        this.ongoingMatches.add(this.ongoingMatches.remove());
      }
    }
  }

  private Predicate<Match> isMatchNotCompleted() {
    return match ->
      match.getStatus() != MatchStatus.FINISHED &&
      match.getStatus() != MatchStatus.AWARDED;
  }

  private boolean isResultAvailable(MatchDTO matchDTO) {
    if (matchDTO.result() == null) {
      return false;
    }

    return matchDTO.result().winner() != null;
  }
}
