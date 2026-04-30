package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.business.IDeleteGoal;
import com.GoalService.GoalService.repository.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteGoalImpl implements IDeleteGoal {

    private final GoalRepository goalRepository;

    @Transactional
    @Override
    public void deleteGoal(long goalId) {
        this.goalRepository.deleteById(goalId);
    }
}
