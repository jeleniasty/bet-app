package com.jeleniasty.betapp.features.scheduler;

import com.jeleniasty.betapp.features.competition.CompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetitionUpdateScheduler {

  private final CompetitionService competitionService;

  @Scheduled(cron = "0 0 0 * * *")
  public void updateCompetitions() {
    var updatedCompetitions = competitionService.updateOngoingCompetitions();
    String message;
    if (updatedCompetitions.isEmpty()) {
      message = "Nothing to update";
    } else {
      message = updatedCompetitions.toString();
    }
    log.info("Attempt to updated competitions and its matches:\n" + message);
  }
}
