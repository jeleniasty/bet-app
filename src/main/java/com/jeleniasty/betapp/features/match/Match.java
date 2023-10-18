package com.jeleniasty.betapp.features.match;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "match")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
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

  @Column(name = "competition", nullable = false)
  private long competition;

  @Column(name = "result")
  private long result;

  @Column(name = "home_team", nullable = false)
  private long homeTeam;

  @Column(name = "away_team", nullable = false)
  private long awayTeam;

  public Match(
    @NotNull MatchStatus status,
    @NotNull CompetitionStage stage,
    char group,
    float homeOdds,
    float awayOdds,
    @NotNull LocalDateTime utcDate,
    long competition,
    long homeTeam,
    long awayTeam
  ) {
    this.status = status;
    this.stage = stage;
    this.group = group;
    this.homeOdds = homeOdds;
    this.awayOdds = awayOdds;
    this.utcDate = utcDate;
    this.competition = competition;
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
  }
}
