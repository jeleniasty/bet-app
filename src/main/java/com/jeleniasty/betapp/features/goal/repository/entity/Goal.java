package com.jeleniasty.betapp.features.goal.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "betapp")
public class Goal {

    @Id
    @SequenceGenerator(name = "betapp.goal_id_seq",
            sequenceName = "betapp.goal_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "betapp.goal_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    private LocalDateTime timestamp;

    @NonNull
    private Long matchId;

    @NonNull
    private Long playerId;


}
