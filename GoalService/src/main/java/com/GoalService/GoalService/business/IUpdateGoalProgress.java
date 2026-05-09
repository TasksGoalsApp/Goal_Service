package com.GoalService.GoalService.business;

import com.GoalService.GoalService.domain.UpdateGoalProgressRequest;
import com.GoalService.GoalService.domain.UpdateGoalProgressResponse;

public interface IUpdateGoalProgress {
    UpdateGoalProgressResponse updateGoalProgress(UpdateGoalProgressRequest request, long userId, long goalId);
}
