package com.jeleniasty.betapp.features.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

  private final TaskScheduler taskScheduler;

  private final Map<Long, ScheduledFuture<?>> scheduledTasksMap =
    new HashMap<>();

  public void scheduleTask(Task task) {
    var scheduledTask =
      this.taskScheduler.scheduleAtFixedRate(
          task.task(),
          task.delay(),
          task.period()
        );

    this.scheduledTasksMap.put(task.name(), scheduledTask);
    log.info(
      "Task with id '" +
      task.name() +
      "' scheduled. Starting at " +
      task.delay()
    );
  }

  public void cancelScheduledTask(Long taskId) {
    var scheduledTask = this.scheduledTasksMap.get(taskId);
    if (scheduledTask != null) {
      scheduledTask.cancel(true);
      this.scheduledTasksMap.remove(taskId);
      log.info("Task with id '" + taskId + "' removed.");
    }
  }
}
