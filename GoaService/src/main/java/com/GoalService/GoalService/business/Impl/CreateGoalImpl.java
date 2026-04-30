package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.business.ICreateGoal;
import com.GoalService.GoalService.domain.CreateGoalRequest;
import com.GoalService.GoalService.domain.CreateGoalResponse;
import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class CreateGoalImpl implements ICreateGoal {
    private final GoalRepository goalRepository;

    @Transactional
    @Override
    public CreateGoalResponse createGoal(CreateGoalRequest request) {

        if (request.getTargetDate() == null || !request.getTargetDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Target date must be in the future");
        }

        GoalEntity savedGoal = saveNewGoal(request);
        return CreateGoalResponse.builder()
                .goalId(savedGoal.getId())
                .build();
    }

    private GoalEntity saveNewGoal (CreateGoalRequest request){

        GoalEntity newgoal = GoalEntity.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .targetDate(request.getTargetDate())
                .category(request.getCategory())
                .progress(0)
                .status(Status.IN_PROGRESS)
                .build();

        return goalRepository.save(newgoal);

    }

}
