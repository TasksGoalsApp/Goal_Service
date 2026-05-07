package com.GoalService.GoalService.business;

import com.GoalService.GoalService.domain.UpdateGoalRequest;
import com.GoalService.GoalService.domain.UpdateGoalResponse;

public interface IUpdateGoal {
    UpdateGoalResponse updateGoal(UpdateGoalRequest updateGoalRequest,  long userId);
}
