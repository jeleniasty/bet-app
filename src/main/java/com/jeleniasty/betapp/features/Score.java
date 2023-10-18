package com.jeleniasty.betapp.features;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "score")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
public class Score {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "score_id_seq",
    sequenceName = "score_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "score_id_seq"
  )
  @Column(name = "id", updatable = false)
  private long id;

  @Column(name = "home")
  private int home;

  @Column(name = "away")
  private int away;
}
