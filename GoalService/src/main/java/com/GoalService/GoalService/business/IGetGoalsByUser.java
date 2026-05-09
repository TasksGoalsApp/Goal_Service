package com.GoalService.GoalService.business;

import com.GoalService.GoalService.domain.GetGoalsByUserResponse;

public interface IGetGoalsByUser {
    GetGoalsByUserResponse getGoalsByUser(long userId);
}
