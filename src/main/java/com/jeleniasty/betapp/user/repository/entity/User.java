package com.jeleniasty.betapp.user.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "betapp")
public class User {

    @Id
    @SequenceGenerator(name = "betapp.user_id_seq",
    sequenceName = "betapp.user_id_seq",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "betapp.user_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    private String login;
    private String password;
    private String email;
    private int points;

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
