package com.jeleniasty.betapp.features.match;

import com.jeleniasty.betapp.features.competition.Competition;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.team.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "match")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
@Setter
public class Match {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "match_id_seq",
    sequenceName = "match_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "match_id_seq"
  )
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  private MatchStatus status;

  @Column(name = "stage", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  private CompetitionStage stage;

  @Column(name = "\"group\"")
  private char group;

  @Column(name = "home_odds")
  private float homeOdds;

  @Column(name = "away_odds")
  private float awayOdds;

  @Column(name = "utc_date", nullable = false)
  @NotNull
  private LocalDateTime utcDate;

  @Column(name = "created_at", updatable = false, nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at", insertable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "competition", nullable = false)
  private Competition competition;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "result")
  private Result result;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "home_team", nullable = false)
  private Team homeTeam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "away_team", nullable = false)
  private Team awayTeam;

  public Match(
    @NotNull MatchStatus status,
    @NotNull CompetitionStage stage,
    char group,
    float homeOdds,
    float awayOdds,
    @NotNull LocalDateTime utcDate
  ) {
    this.status = status;
    this.stage = stage;
    this.group = group;
    this.homeOdds = homeOdds;
    this.awayOdds = awayOdds;
    this.utcDate = utcDate;
  }

  public void assignCompetition(Competition competition) {
    competition.getCompetitionMatches().add(this);
    this.setCompetition(competition);
  }

  public void assignHomeTeam(Team homeTeam) {
    homeTeam.getHomeMatches().add(this);
    this.setHomeTeam(homeTeam);
  }

  public void assignAwayTeam(Team awayTeam) {
    awayTeam.getAwayMatches().add(this);
    this.setAwayTeam(awayTeam);
  }
}
