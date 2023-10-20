package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.features.match.Match;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "team")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
@Setter
public class Team {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "team_id_seq",
    sequenceName = "team_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq")
  private Long id;

  @NotNull
  @Column(name = "name")
  private String name;

  @NotNull
  @Column(name = "code")
  private String code;

  @NotNull
  @Column(name = "flag")
  private String flag;

  @OneToMany(
    mappedBy = "homeTeam",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Match> homeMatches = new HashSet<>();

  @OneToMany(
    mappedBy = "awayTeam",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Match> awayMatches = new HashSet<>();

  public Team(
    @NotNull String name,
    @NotNull String code,
    @NotNull String flag
  ) {
    this.name = name;
    this.code = code;
    this.flag = flag;
  }
}
