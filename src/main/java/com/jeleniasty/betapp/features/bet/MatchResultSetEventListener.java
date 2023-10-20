package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.MatchResultSetEvent;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchResultSetEventListener
  implements ApplicationListener<MatchResultSetEvent> {

  private final BetService betService;

  @Override
  public void onApplicationEvent(
    @NotNull MatchResultSetEvent matchResultSetEvent
  ) {
    var matchId = matchResultSetEvent.getSource();
    betService.assignPoints((Long) matchId);
  }
}
