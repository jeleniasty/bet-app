package com.jeleniasty.betapp.features.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

  private final TaskScheduler taskScheduler;

  public void scheduleTaskExecution(DelayedTask task) {
    log.info("Scheduling task with id: " + task.id());
    this.taskScheduler.schedule(task.task(), task.delay());
  }
}
