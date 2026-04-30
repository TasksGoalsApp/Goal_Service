package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.business.IUpdateGoal;
import com.GoalService.GoalService.domain.UpdateGoalRequest;
import com.GoalService.GoalService.domain.UpdateGoalResponse;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateGoalImpl implements IUpdateGoal {

    private final GoalRepository goalRepository;

    @Transactional
    @Override
    public UpdateGoalResponse updateGoal(UpdateGoalRequest updateGoalRequest) {
        GoalEntity goalEntity = goalRepository.findByIdAndUserId(updateGoalRequest.getId(), updateGoalRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        goalEntity.setTitle(updateGoalRequest.getTitle());
        goalEntity.setDescription(updateGoalRequest.getDescription());
        goalEntity.setTargetDate(updateGoalRequest.getTargetDate());
        goalEntity.setProgress(updateGoalRequest.getProgress());
        goalEntity.setCategory(updateGoalRequest.getCategory());
        goalEntity.setStatus(updateGoalRequest.getStatus());

        GoalEntity savedGoal = goalRepository.save(goalEntity);

        return UpdateGoalResponse.builder()
                .id(savedGoal.getId())
                .title(savedGoal.getTitle())
                .userId(savedGoal.getUserId())
                .description(savedGoal.getDescription())
                .targetDate(savedGoal.getTargetDate())
                .progress(savedGoal.getProgress())
                .category(savedGoal.getCategory())
                .status(savedGoal.getStatus())
                .build();
    }


}
