package com.jeleniasty.betapp.features.scheduler.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchResultTask implements Runnable {

  @Override
  public final void run() {
    log.info("Sprawdzam czy result juz jest");
  }
}
