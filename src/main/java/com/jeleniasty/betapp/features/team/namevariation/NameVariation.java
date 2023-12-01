package com.jeleniasty.betapp.features.team.namevariation;

import com.jeleniasty.betapp.features.team.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "name_variation")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
@Setter
public class NameVariation {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "name_variation_seq",
    sequenceName = "name_variation_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "name_variation_seq"
  )
  @Column(name = "id", updatable = false)
  private Long id;

  @NotNull
  @Column(name = "variation")
  private String variation;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "team")
  private Team team;
}
