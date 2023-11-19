package com.jeleniasty.betapp.features.team;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
  Optional<Team> findByNameAndCode(String name, String code);
}
