package com.jeleniasty.betapp.features.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetappUserRepository extends JpaRepository<BetappUser, Long> {
  Optional<BetappUser> findByUsernameOrEmail(String username, String email);
}
