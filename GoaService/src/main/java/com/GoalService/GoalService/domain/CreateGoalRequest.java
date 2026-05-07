package com.GoalService.GoalService.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoalRequest {
    @NotBlank
    @Size(max = 50)
    private String title;
    @NotBlank
    @Size(max = 150)
    private String description;
    @NotNull
    private LocalDate targetDate;
    @NotNull
    private Category category;

}
