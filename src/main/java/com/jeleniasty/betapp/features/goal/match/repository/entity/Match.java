package com.jeleniasty.betapp.features.goal.match.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(schema = "betapp")
public class Match {

    @Id
    @SequenceGenerator(name = "betapp.match_id_seq",
            sequenceName = "betapp.match_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "betapp.match_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    private Integer homeTeamScore;

    private Integer awayTeamScore;

    private LocalDateTime endTime;

    @NonNull
    private String homeTeamCode;

    @NonNull
    private String awayTeamCode;

//    public Match(Integer homeTeamScore, Integer awayTeamScore, @NonNull LocalDateTime endTime, @NonNull String homeTeamCode, @NonNull String awayTeamCode) {
//        this.homeTeamScore = homeTeamScore;
//        this.awayTeamScore = awayTeamScore;
//        this.endTime = endTime;
//        this.homeTeamCode = homeTeamCode;
//        this.awayTeamCode = awayTeamCode;
//    }

}
