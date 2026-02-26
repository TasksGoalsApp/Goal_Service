package com.GoaService.GoaService.business.Impl;

import com.GoaService.GoaService.business.IUpdateGoal;
import com.GoaService.GoaService.domain.CreateGoalRequest;
import com.GoaService.GoaService.domain.UpdateGoalResponse;
import com.GoaService.GoaService.repository.GoalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
@Builder
@AllArgsConstructor
public class UpdateGoalImpl implements IUpdateGoal {

    private final GoalRepository goalRepository;

    @Transactional
    @Override
    public UpdateGoalResponse updateGoal(CreateGoalRequest createGoalRequest) {

        return null;
    }


}
