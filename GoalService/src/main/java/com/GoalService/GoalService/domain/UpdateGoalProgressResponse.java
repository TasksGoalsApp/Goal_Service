package com.GoalService.GoalService.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateGoalProgressResponse {
    int progress;
}
