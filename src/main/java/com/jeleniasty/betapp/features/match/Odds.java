package com.jeleniasty.betapp.features.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Odds {

  private float home;
  private float away;

  public Odds(float home, float away) {
    this.home = home;
    this.away = away;
  }
}
