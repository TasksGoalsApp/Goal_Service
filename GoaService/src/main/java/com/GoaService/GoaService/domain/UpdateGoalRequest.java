package com.GoaService.GoaService.domain;

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
    @NotNull
    private long userId;
    @NotBlank
    private String title;
    private String description;
    private LocalDate targetDate;
    @NotNull
    private long progress;
    @NotBlank
    private Category category;
    @NotBlank
    private Status status;
}