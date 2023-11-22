package com.jeleniasty.betapp.httpclient.match;

import com.jeleniasty.betapp.features.competition.CompetitionType;
import com.jeleniasty.betapp.features.match.CompetitionStage;
import com.jeleniasty.betapp.features.match.Group;
import com.jeleniasty.betapp.features.match.MatchStatus;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.Winner;
import java.time.LocalDate;
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
  public static class Filters {

    private int season;
  }

  @Getter
  @Setter
  public static class ResultSet {

    private int count;
    private LocalDate first;
    private LocalDate last;
    private int played;
  }

  @Getter
  @Setter
  public static class CompetitionResponse {

    private String name;
    private String code;
    private CompetitionType type;
    private String emblem;
  }

  @Getter
  @Setter
  public static class MatchResponse {

    private MatchStatus status;
    private CompetitionStage stage;
    private Group group;
    private LocalDateTime utcDate;
    private TeamResponse homeTeam;
    private TeamResponse awayTeam;
    private ScoreResponse score;
  }

  @Getter
  @Setter
  public static class TeamResponse {

    private String name;
    private String tla;
    private String crest;
  }

  @Getter
  @Setter
  public static class ScoreResponse {

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
  public static class BasicScoreResponse {

    private int home;
    private int away;
  }
}
