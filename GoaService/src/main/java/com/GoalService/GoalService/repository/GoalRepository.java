package com.GoalService.GoalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

    List<GoalEntity> findGoalsByUserId(long userId);
    Optional<GoalEntity> findByIdAndUserId(long id, long userId);
}
