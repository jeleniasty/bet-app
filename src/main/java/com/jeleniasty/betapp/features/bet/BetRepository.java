package com.jeleniasty.betapp.features.bet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
  List<Bet> findAllByMatchId(long matchId);

  @Query(
    "select b from bet b where b.match.id = :matchId and b.player.id = :playerId"
  )
  List<Bet> findAllByMatchAndAndPlayer(
    @Param("matchId") long matchId,
    @Param("playerId") long playerId
  );
}
