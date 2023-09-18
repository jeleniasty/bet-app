package com.jeleniasty.betapp.features.user.repository;

import com.jeleniasty.betapp.features.user.repository.entity.BetappUser;
import com.jeleniasty.betapp.features.user.repository.entity.BetappUserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetappUserRepository extends JpaRepository<BetappUser, Long> {
  Optional<BetappUser> findByEmail(String email);

  List<BetappUser> findAllByBetappUserRole(BetappUserRole betappUserRole);
}
