package com.jeleniasty.betapp.features.competition;

import com.jeleniasty.betapp.features.match.Match;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "competition")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
@Setter
public class Competition {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "competition_id_seq",
    sequenceName = "competition_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "competition_id_seq"
  )
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "code", nullable = false)
  private String code;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private CompetitionType type;

  @Column(name = "season", nullable = false)
  private int season;

  @OneToMany(
    mappedBy = "competition",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Match> competitionMatches = new HashSet<>();

  public Competition(
    String name,
    String code,
    CompetitionType type,
    int season
  ) {
    this.name = name;
    this.code = code;
    this.type = type;
    this.season = season;
  }
}
