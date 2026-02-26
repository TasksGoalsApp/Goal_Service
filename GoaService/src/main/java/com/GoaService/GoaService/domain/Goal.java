package com.GoaService.GoaService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    private long id;
    private long userId;
    private String title;
    private String description;
    private LocalDate targetDate;
    private long progress;
    private Category category;
    private Status status;

}
