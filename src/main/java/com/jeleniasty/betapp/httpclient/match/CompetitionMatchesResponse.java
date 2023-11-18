package com.jeleniasty.betapp.httpclient.match;

import com.jeleniasty.betapp.features.match.CompetitionStage;
import com.jeleniasty.betapp.features.match.MatchStatus;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.Winner;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitionMatchesResponse {

  private Filters filters;
  private ResultSet resultSet;

  private CompetitionResponse competition;

  private List<MatchResponse> matches;

  @Getter
  @Setter
  private static class Filters {

    private String season;
  }

  @Getter
  @Setter
  private static class ResultSet {

    private int count;
    private String first;
    private String last;
    private int played;
  }

  @Getter
  @Setter
  private static class CompetitionResponse {

    private String name;
    private String code;
    private String type;
  }

  @Getter
  @Setter
  private static class MatchResponse {

    private MatchStatus status;
    private CompetitionStage stage;
    private String group;
    private LocalDateTime utcDate;
    private TeamResponse homeTeam;
    private TeamResponse awayTeam;
    private ScoreResponse score;
  }

  @Getter
  @Setter
  private static class TeamResponse {

    private String name;
    private String tla;
  }

  @Getter
  @Setter
  private static class ScoreResponse {

    private Winner winner;
    private Duration duration;
    private BasicScoreResponse fullTime;
    private BasicScoreResponse halfTime;
    private BasicScoreResponse regularTime;
    private BasicScoreResponse extraTime;
    private BasicScoreResponse penalties;
  }

  @Getter
  @Setter
  private static class BasicScoreResponse {

    private int home;
    private int away;
  }
}
