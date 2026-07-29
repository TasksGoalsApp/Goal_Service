package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.domain.UpdateGoalProgressRequest;
import com.GoalService.GoalService.domain.UpdateGoalProgressResponse;
import com.GoalService.GoalService.exception.ResourceNotFoundException;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateGoalProgressImplTest {

    @Mock
    private GoalRepository goalRepository;

    private UpdateGoalProgressImpl updateGoalProgress;

    @BeforeEach
    void setUp() {
        updateGoalProgress = new UpdateGoalProgressImpl(goalRepository);
    }

    @Test
    void updateGoalProgressShouldSetNotStartedForZeroProgress() {
        assertProgressAndStatus(0, Status.NOT_STARTED);
    }

    @Test
    void updateGoalProgressShouldSetInProgressForPartialProgress() {
        assertProgressAndStatus(45, Status.IN_PROGRESS);
    }

    @Test
    void updateGoalProgressShouldSetCompletedForOneHundredProgress() {
        assertProgressAndStatus(100, Status.COMPLETED);
    }

    @Test
    void updateGoalProgressShouldThrowWhenGoalDoesNotExistForUser() {
        when(goalRepository.findByIdAndUserId(11L, 2L)).thenReturn(Optional.empty());
        UpdateGoalProgressRequest request = UpdateGoalProgressRequest.builder().progress(50).build();

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> updateGoalProgress.updateGoalProgress(request, 2L, 11L)
        );

        assertEquals("Goal not found", exception.getMessage());
        verify(goalRepository, never()).save(any());
    }

    private void assertProgressAndStatus(int progress, Status expectedStatus) {
        long goalId = 11L;
        long userId = 2L;
        GoalEntity goal = GoalEntity.builder()
                .id(goalId)
                .userId(userId)
                .progress(10)
                .status(Status.IN_PROGRESS)
                .build();
        when(goalRepository.findByIdAndUserId(goalId, userId)).thenReturn(Optional.of(goal));
        when(goalRepository.save(goal)).thenReturn(goal);
        UpdateGoalProgressRequest request = UpdateGoalProgressRequest.builder().progress(progress).build();

        UpdateGoalProgressResponse response = updateGoalProgress.updateGoalProgress(request, userId, goalId);

        assertEquals(progress, response.getProgress());
        assertEquals(progress, goal.getProgress());
        assertEquals(expectedStatus, goal.getStatus());
        verify(goalRepository).save(goal);
    }
}
