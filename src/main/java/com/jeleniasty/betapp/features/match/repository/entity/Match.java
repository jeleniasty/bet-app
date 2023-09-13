package com.jeleniasty.betapp.features.match.repository.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(schema = "betapp")
public class Match {

  @Id
  @SequenceGenerator(
    name = "betapp.match_id_seq",
    sequenceName = "betapp.match_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "betapp.match_id_seq"
  )
  @Column(name = "id", updatable = false)
  private Long id;

  private Integer homeTeamScore;

  private Integer awayTeamScore;

  private LocalDateTime started;

  private Integer duration;

  @NonNull
  private String homeTeamCode;

  @NonNull
  private String awayTeamCode;
}
