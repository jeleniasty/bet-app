package com.jeleniasty.betapp.features.bet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
  List<Bet> findAllByMatchId(long matchId);
}
