package com.jeleniasty.betapp.features.match;

import org.springframework.context.ApplicationEvent;

public class MatchResultSetEvent extends ApplicationEvent {

  public MatchResultSetEvent(Long matchId) {
    super(matchId);
  }
}
