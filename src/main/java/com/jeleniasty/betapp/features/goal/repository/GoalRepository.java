package com.jeleniasty.betapp.features.goal.repository;

import com.jeleniasty.betapp.features.goal.repository.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {}
