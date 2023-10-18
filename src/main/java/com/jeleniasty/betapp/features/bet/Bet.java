package com.jeleniasty.betapp.features.bet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private long id;

  @Column(name = "match")
  private long match;

  @Column(name = "betapp_user")
  private long player;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  @Version
  @NotNull
  private Integer version;
}
