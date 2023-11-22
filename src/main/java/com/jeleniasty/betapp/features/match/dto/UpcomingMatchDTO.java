package com.jeleniasty.betapp.features.match.dto;

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
