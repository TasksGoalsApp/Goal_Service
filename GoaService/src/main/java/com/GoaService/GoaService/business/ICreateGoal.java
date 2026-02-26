package com.GoaService.GoaService.business;

import com.GoaService.GoaService.domain.CreateGoalRequest;
import com.GoaService.GoaService.domain.CreateGoalResponse;

public interface ICreateGoal {
    CreateGoalResponse createGoal(CreateGoalRequest request);
}
