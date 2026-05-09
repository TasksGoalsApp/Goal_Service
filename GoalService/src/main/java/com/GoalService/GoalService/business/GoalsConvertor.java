package com.GoalService.GoalService.business;

import com.GoalService.GoalService.domain.Goal;
import com.GoalService.GoalService.repository.GoalEntity;

public class GoalsConvertor {

    private GoalsConvertor() {}

    public static Goal convert(GoalEntity goalEntity) {
        return Goal.builder()
                .id(goalEntity.getId())
                .userId(goalEntity.getUserId())
                .title(goalEntity.getTitle())
                .description(goalEntity.getDescription())
                .targetDate(goalEntity.getTargetDate())
                .progress(goalEntity.getProgress())
                .category(goalEntity.getCategory())
                .status(goalEntity.getStatus())
                .build();
    }
}
