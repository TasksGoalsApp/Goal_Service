package com.GoaService.GoaService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGoalsByUserResponse {
    List<Goal> goals;
}
