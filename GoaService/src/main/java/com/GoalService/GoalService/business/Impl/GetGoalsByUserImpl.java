package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.business.GoalsConvertor;
import com.GoalService.GoalService.business.IGetGoalsByUser;
import com.GoalService.GoalService.domain.GetGoalsByUserResponse;
import com.GoalService.GoalService.domain.Goal;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class GetGoalsByUserImpl implements IGetGoalsByUser {

    private final GoalRepository goalRepository;

    @Transactional
    @Override
    public GetGoalsByUserResponse getGoalsByUser(long userId) {
        List<GoalEntity> goalEntities = goalRepository.findGoalsByUserId(userId);
        List<Goal> goals = goalEntities.stream()
                .map(GoalsConvertor ::convert)
                .toList();


        return GetGoalsByUserResponse.builder()
                .goals(goals)
                .build();
    }
}
