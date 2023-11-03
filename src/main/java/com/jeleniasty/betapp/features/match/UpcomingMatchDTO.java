package com.jeleniasty.betapp.features.match;

public interface UpcomingMatchDTO {
  Long getId();
  String getHomeTeam();
  String getHomeFlag();
  String getAwayTeam();
  String getAwayFlag();
  String getHomeOdds();
  String getAwayOdds();
  String getMatchDate();
}
