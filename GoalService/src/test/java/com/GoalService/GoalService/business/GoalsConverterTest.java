package com.GoalService.GoalService.business;

import com.GoalService.GoalService.domain.Category;
import com.GoalService.GoalService.domain.Goal;
import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.repository.GoalEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoalsConverterTest {

    @Test
    void convertShouldMapAllFields() {
        GoalEntity entity = GoalEntity.builder()
                .id(1L)
                .userId(2L)
                .title("Goal")
                .description("Description")
                .targetDate(LocalDate.of(2027, 1, 15))
                .progress(75)
                .category(Category.Professional)
                .status(Status.IN_PROGRESS)
                .build();

        Goal goal = GoalsConvertor.convert(entity);

        assertEquals(entity.getId(), goal.getId());
        assertEquals(entity.getUserId(), goal.getUserId());
        assertEquals(entity.getTitle(), goal.getTitle());
        assertEquals(entity.getDescription(), goal.getDescription());
        assertEquals(entity.getTargetDate(), goal.getTargetDate());
        assertEquals(entity.getProgress(), goal.getProgress());
        assertEquals(entity.getCategory(), goal.getCategory());
        assertEquals(entity.getStatus(), goal.getStatus());
    }
}
