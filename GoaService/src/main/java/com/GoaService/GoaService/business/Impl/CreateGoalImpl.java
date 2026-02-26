package com.GoaService.GoaService.business.Impl;

import com.GoaService.GoaService.business.ICreateGoal;
import com.GoaService.GoaService.domain.CreateGoalRequest;
import com.GoaService.GoaService.domain.CreateGoalResponse;
import com.GoaService.GoaService.domain.Status;
import com.GoaService.GoaService.repository.GoalEntity;
import com.GoaService.GoaService.repository.GoalRepository;
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
                .progress(request.getProgress())
                .status(Status.IN_PROGRESS)
                .build();

        return goalRepository.save(newgoal);

    }

}
