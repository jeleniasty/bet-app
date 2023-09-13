package com.jeleniasty.betapp.features.matchbet.repository.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "betapp")
public class MatchBet {

  @Id
  @SequenceGenerator(
    name = "betapp.match_bet_id_seq",
    sequenceName = "betapp.match_bet_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "betapp.match_bet_id_seq"
  )
  @Column(name = "id", updatable = false)
  private Long id;

  @NonNull
  private Integer homeTeamScore;

  @NonNull
  private Integer awayTeamScore;

  @NonNull
  private LocalDateTime created;

  private LocalDateTime updated;

  @NonNull
  private Long userId;

  @NonNull
  private Long matchId;
}
