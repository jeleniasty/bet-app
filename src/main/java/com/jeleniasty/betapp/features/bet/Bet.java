package com.jeleniasty.betapp.features.bet;

import com.jeleniasty.betapp.features.match.model.Match;
import com.jeleniasty.betapp.features.result.Result;
import com.jeleniasty.betapp.features.user.BetappUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private BetType betType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "match")
  private Match match;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "betapp_user")
  private BetappUser player;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "result")
  private Result result;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  @NotNull
  @Version
  private Integer version;

  public Bet(Result result, @NotNull BetType betType) {
    this.betType = betType;
    this.result = result;
  }

  public void assignMatch(@NotNull Match match) {
    match.getMatchBets().add(this);
    setMatch(match);
  }

  public void assignPlayer(@NotNull BetappUser betappUser) {
    betappUser.getPlayerBets().add(this);
    setPlayer(betappUser);
  }
}
