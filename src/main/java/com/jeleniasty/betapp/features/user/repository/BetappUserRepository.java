package com.jeleniasty.betapp.features.user.repository;

import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetappUserRepository extends JpaRepository<BetappUser, Long> {
  Optional<BetappUser> findByEmail(String email);

  @Modifying
  @Query(
    "update BetappUser b set b.points = b.points + :newPoints where b.id = :userId"
  )
  void updateBetappUserById(
    @Param("newPoints") Integer newPoints,
    @Param("userId") Long userId
  );
}
