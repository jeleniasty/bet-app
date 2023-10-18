package com.jeleniasty.betapp.features.result;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "result")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
public class Result {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "result_id_seq",
    sequenceName = "result_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "result_id_seq"
  )
  @Column(name = "id", updatable = false)
  private long id;

  @Column(name = "winner", nullable = false)
  @Enumerated(EnumType.STRING)
  private Winner winner;

  @Column(name = "duration", nullable = false)
  private Duration duration;

  @Column(name = "half_time")
  private long halfTimeScore;

  @Column(name = "regular_time")
  private long regularTimeScore;

  @Column(name = "extra_time")
  private long extraTimeScore;

  @Column(name = "penalties")
  private long penaltiesScore;
}
