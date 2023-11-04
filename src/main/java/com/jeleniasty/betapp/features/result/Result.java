package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.score.Score;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
  @Column(name = "id")
  private long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "winner")
  private Winner winner;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "duration")
  private Duration duration;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "half_time")
  private Score halfTimeScore;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "regular_time")
  private Score regularTimeScore;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "extra_time")
  private Score extraTimeScore;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "penalties")
  private Score penaltiesScore;

  public Result(
    @NotNull Winner winner,
    @NotNull Duration duration,
    @NotNull Score halfTimeScore,
    @NotNull Score regularTimeScore,
    Score extraTimeScore,
    Score penaltiesScore
  ) {
    this.winner = winner;
    this.duration = duration;
    this.halfTimeScore = halfTimeScore;
    this.regularTimeScore = regularTimeScore;
    this.extraTimeScore = extraTimeScore;
    this.penaltiesScore = penaltiesScore;
  }
}
