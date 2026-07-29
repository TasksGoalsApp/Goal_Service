package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.domain.Category;
import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.domain.UpdateGoalRequest;
import com.GoalService.GoalService.domain.UpdateGoalResponse;
import com.GoalService.GoalService.exception.ResourceNotFoundException;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateGoalImplTest {

    @Mock
    private GoalRepository goalRepository;

    private UpdateGoalImpl updateGoal;

    @BeforeEach
    void setUp() {
        updateGoal = new UpdateGoalImpl(goalRepository);
    }

    @Test
    void updateGoalShouldUpdateOwnedGoalAndResolveStatus() {
        long userId = 3L;
        GoalEntity existingGoal = GoalEntity.builder()
                .id(7L)
                .userId(userId)
                .title("Old title")
                .progress(0)
                .status(Status.NOT_STARTED)
                .category(Category.Personal)
                .build();
        UpdateGoalRequest request = UpdateGoalRequest.builder()
                .id(7L)
                .title("New title")
                .description("New description")
                .targetDate(LocalDate.of(2027, 3, 10))
                .progress(60)
                .category(Category.Professional)
                .build();

        when(goalRepository.findByIdAndUserId(7L, userId)).thenReturn(Optional.of(existingGoal));
        when(goalRepository.save(existingGoal)).thenReturn(existingGoal);

        UpdateGoalResponse response = updateGoal.updateGoal(request, userId);

        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(request.getTargetDate(), response.getTargetDate());
        assertEquals(request.getProgress(), response.getProgress());
        assertEquals(request.getCategory(), response.getCategory());
        assertEquals(Status.IN_PROGRESS, response.getStatus());
        assertEquals(userId, response.getUserId());
        verify(goalRepository).save(existingGoal);
    }

    @Test
    void updateGoalShouldIgnoreStatusFromRequestAndDeriveCompletedStatus() {
        long userId = 3L;
        GoalEntity existingGoal = GoalEntity.builder().id(7L).userId(userId).build();
        UpdateGoalRequest request = UpdateGoalRequest.builder()
                .id(7L)
                .title("Finished goal")
                .progress(100)
                .category(Category.Personal)
                .status(Status.NOT_STARTED)
                .build();

        when(goalRepository.findByIdAndUserId(7L, userId)).thenReturn(Optional.of(existingGoal));
        when(goalRepository.save(existingGoal)).thenReturn(existingGoal);

        UpdateGoalResponse response = updateGoal.updateGoal(request, userId);

        assertEquals(Status.COMPLETED, response.getStatus());
    }

    @Test
    void updateGoalShouldThrowWhenGoalDoesNotBelongToUser() {
        UpdateGoalRequest request = UpdateGoalRequest.builder().id(7L).build();
        when(goalRepository.findByIdAndUserId(7L, 3L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> updateGoal.updateGoal(request, 3L)
        );

        assertEquals("Goal not found", exception.getMessage());
        verify(goalRepository, never()).save(any());
    }
}
