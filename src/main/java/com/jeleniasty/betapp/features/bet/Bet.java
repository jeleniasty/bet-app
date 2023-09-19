package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.Match;
import com.jeleniasty.betapp.features.match.result.MatchResult;
import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "bet")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
@Setter
public class Bet {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "bet_id_seq",
    sequenceName = "bet_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bet_id_seq")
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "match_result_id", referencedColumnName = "id")
  private MatchResult matchResult;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private BetappUser betPlayer;

  @ManyToOne
  @JoinColumn(name = "match_id", referencedColumnName = "id")
  private Match match;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  @Version
  @NotNull
  private Integer version;

  public Bet(MatchResult matchResult) {
    this.matchResult = matchResult;
  }
}
