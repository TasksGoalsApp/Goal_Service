package com.GoaService.GoaService.business;

import com.GoaService.GoaService.domain.CreateGoalRequest;
import com.GoaService.GoaService.domain.UpdateGoalResponse;

public interface IUpdateGoal {
    UpdateGoalResponse updateGoal(CreateGoalRequest createGoalRequest);
}
