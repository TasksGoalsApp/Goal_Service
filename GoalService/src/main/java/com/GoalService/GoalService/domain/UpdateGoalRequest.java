package com.GoalService.GoalService.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateGoalRequest {

    @NotNull
    private long id;

    @NotBlank
    private String title;

    private String description;

    private LocalDate targetDate;
    @NotNull
    @Min(0)
    @Max(100)
    private int progress;
    @NotNull
    private Category category;
    @NotNull
    private Status status;
}