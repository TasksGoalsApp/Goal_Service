package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.business.IDeleteGoal;
import com.GoalService.GoalService.exception.ResourceNotFoundException;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteGoalImpl implements IDeleteGoal {

    private final GoalRepository goalRepository;

    @Transactional
    @Override
    public void deleteGoal(long goalId, long userId) {
        GoalEntity goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        this.goalRepository.delete(goal);
    }
}
