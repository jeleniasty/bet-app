package com.jeleniasty.betapp.features.match;

import org.springframework.context.ApplicationEvent;

public class MatchCompletionEvent extends ApplicationEvent {

  public MatchCompletionEvent(Long matchId) {
    super(matchId);
  }
}
