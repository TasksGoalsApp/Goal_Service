package com.GoalService.GoalService.business;

import com.GoalService.GoalService.domain.CreateGoalRequest;
import com.GoalService.GoalService.domain.CreateGoalResponse;

public interface ICreateGoal {
    CreateGoalResponse createGoal(CreateGoalRequest request, long userId);
}
