package com.jeleniasty.betapp.features.user.repository;

import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BetappUserRepository extends JpaRepository<BetappUser, Long> {
  Optional<BetappUser> findByEmail(String email);

}
