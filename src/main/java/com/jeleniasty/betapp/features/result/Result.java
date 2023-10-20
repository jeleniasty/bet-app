package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.Score;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

  @OneToOne
  @JoinColumn(name = "half_time", nullable = false)
  private Score halfTimeScore;

  @OneToOne
  @JoinColumn(name = "regular_time", nullable = false)
  private Score regularTimeScore;

  @OneToOne
  @JoinColumn(name = "extra_time")
  private Score extraTimeScore;

  @OneToOne
  @JoinColumn(name = "penalties")
  private Score penaltiesScore;
}
