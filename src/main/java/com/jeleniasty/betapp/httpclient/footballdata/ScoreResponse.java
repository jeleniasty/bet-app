package com.jeleniasty.betapp.httpclient.footballdata;

import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.Winner;

public record ScoreResponse(
  Winner winner,
  Duration duration,
  BasicScoreResponse fullTime,
  BasicScoreResponse halfTime,
  BasicScoreResponse regularTime,
  BasicScoreResponse extraTime,
  BasicScoreResponse penalties
) {
  public record BasicScoreResponse(Integer home, Integer away) {}
}
