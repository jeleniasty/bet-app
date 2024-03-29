package com.jeleniasty.betapp.features.match.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jeleniasty.betapp.features.bet.Bet;
import com.jeleniasty.betapp.features.competition.Competition;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.team.Team;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
@JsonIgnoreProperties({ "competition" })
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

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private MatchStatus status;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "stage")
  private CompetitionStage stage;

  @Enumerated(EnumType.STRING)
  @Column(name = "\"group\"")
  private Group group;

  @Column(name = "home_odds")
  private float homeOdds;

  @Column(name = "away_odds")
  private float awayOdds;

  @Column(name = "draw_odds")
  private float drawOdds;

  @NotNull
  @Column(name = "date")
  private LocalDateTime date;

  @NotNull
  @Column(name = "external_id", unique = true)
  private Long externalId;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "competition")
  private Competition competition;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "result")
  private Result result;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "home_team")
  private Team homeTeam;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "away_team")
  private Team awayTeam;

  @OneToMany(
    mappedBy = "match",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Bet> matchBets = new HashSet<>();

  public Match(
    @NotNull MatchStatus status,
    @NotNull CompetitionStage stage,
    Group group,
    float homeOdds,
    float awayOdds,
    float drawOdds,
    @NotNull LocalDateTime date,
    @NotNull Long externalId
  ) {
    this.status = status;
    this.stage = stage;
    this.group = group;
    this.homeOdds = homeOdds;
    this.awayOdds = awayOdds;
    this.drawOdds = drawOdds;
    this.date = date;
    this.externalId = externalId;
  }

  public void assignCompetition(Competition competition) {
    competition.getCompetitionMatches().add(this);
    this.setCompetition(competition);
  }
}
