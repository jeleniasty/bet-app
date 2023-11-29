package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.MatchCompletionEvent;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchCompletionListener
  implements ApplicationListener<MatchCompletionEvent> {

  private final BetService betService;

  @Override
  public void onApplicationEvent(
    @NotNull MatchCompletionEvent matchCompletionEvent
  ) {
    var matchId = matchCompletionEvent.getSource();
    betService.assignPoints((Long) matchId);
  }
}
