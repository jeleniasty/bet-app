package com.jeleniasty.betapp.matchbet.repository;

import com.jeleniasty.betapp.matchbet.repository.entity.MatchBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchBetRepository extends JpaRepository<MatchBet, Long> {
}
