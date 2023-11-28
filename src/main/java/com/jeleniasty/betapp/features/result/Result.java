package com.jeleniasty.betapp.features.result;

import com.jeleniasty.betapp.features.result.score.Score;
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
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "full_time")
  private Score fullTimeScore;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  public Result(
    @NotNull Winner winner,
    Duration duration,
    Score halfTimeScore,
    Score regularTimeScore,
    Score extraTimeScore,
    Score penaltiesScore,
    Score fullTimeScore
  ) {
    this.winner = winner;
    this.duration = duration;
    this.halfTimeScore = halfTimeScore;
    this.regularTimeScore = regularTimeScore;
    this.extraTimeScore = extraTimeScore;
    this.penaltiesScore = penaltiesScore;
    this.fullTimeScore = fullTimeScore;
  }
}
