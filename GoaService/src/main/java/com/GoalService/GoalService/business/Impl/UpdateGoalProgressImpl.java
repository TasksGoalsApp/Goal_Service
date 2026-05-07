package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.business.IUpdateGoalProgress;
import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.domain.UpdateGoalProgressRequest;
import com.GoalService.GoalService.domain.UpdateGoalProgressResponse;
import com.GoalService.GoalService.exception.ResourceNotFoundException;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdateGoalProgressImpl implements IUpdateGoalProgress {
    private final GoalRepository goalRepository;

    @Transactional
    @Override
    public UpdateGoalProgressResponse updateGoalProgress(UpdateGoalProgressRequest request, long userId, long goalId) {
        GoalEntity goalEntity = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        goalEntity.setProgress(request.getProgress());
        if(request.getProgress() == 100){
            goalEntity.setStatus(Status.COMPLETED);
        }else {
            goalEntity.setStatus(Status.IN_PROGRESS);
        }

        goalRepository.save(goalEntity);
        return UpdateGoalProgressResponse.builder()
                .progress(goalEntity.getProgress())
                .build();

    }
}
