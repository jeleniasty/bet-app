package com.jeleniasty.betapp.match.repository;

import com.jeleniasty.betapp.match.repository.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
