package com.jeleniasty.betapp.features.user;

import com.jeleniasty.betapp.features.user.role.RoleName;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetappUserRepository extends JpaRepository<BetappUser, Long> {
  Optional<BetappUser> findByEmail(String email);
  List<BetappUser> findByRolesName(RoleName roleName);

  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}
