package com.jeleniasty.betapp.features.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeleniasty.betapp.features.bet.Bet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(name = "home_team_code")
  @NotNull
  private String homeTeamCode;

  @NotNull
  @Column(name = "away_team_code")
  private String awayTeamCode;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "stadium_id")
  private Long stadiumId;

  @JsonIgnore
  @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
  private Set<Bet> matchBets;

  public Match(
    @NotNull String homeTeamCode,
    @NotNull String awayTeamCode,
    LocalDateTime startTime,
    Long stadiumId
  ) {
    this.homeTeamCode = homeTeamCode;
    this.awayTeamCode = awayTeamCode;
    this.startTime = startTime;
    this.stadiumId = stadiumId;
  }
}
