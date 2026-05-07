package com.GoalService.GoalService.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdateGoalResponse {

    private long id;
    private long userId;
    private String title;
    private String description;
    private LocalDate targetDate;
    private int progress;
    private Category category;
    private Status status;
}
