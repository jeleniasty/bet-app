package com.jeleniasty.betapp.features.matchresult;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "match_result")
@Table(schema = "betapp")
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "match_result_id_seq",
    sequenceName = "match_result_id_seq"
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "match_result_id_seq"
  )
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "home_team_score")
  @NotNull
  private Integer homeTeamScore;

  @Column(name = "away_team_score")
  @NotNull
  private Integer awayTeamScore;

  private Integer duration;

  public MatchResult(
    @NotNull Integer homeTeamScore,
    @NotNull Integer awayTeamScore,
    Integer duration
  ) {
    this.homeTeamScore = homeTeamScore;
    this.awayTeamScore = awayTeamScore;
    this.duration = duration;
  }
}
