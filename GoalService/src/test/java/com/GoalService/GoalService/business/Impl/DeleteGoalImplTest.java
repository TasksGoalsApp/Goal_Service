package com.GoalService.GoalService.business.Impl;

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
class DeleteGoalImplTest {

    @Mock
    private GoalRepository goalRepository;

    private DeleteGoalImpl deleteGoal;

    @BeforeEach
    void setUp() {
        deleteGoal = new DeleteGoalImpl(goalRepository);
    }

    @Test
    void deleteGoalShouldDeleteOwnedGoal() {
        GoalEntity goal = GoalEntity.builder().id(5L).userId(8L).build();
        when(goalRepository.findByIdAndUserId(5L, 8L)).thenReturn(Optional.of(goal));

        deleteGoal.deleteGoal(5L, 8L);

        verify(goalRepository).delete(goal);
    }

    @Test
    void deleteGoalShouldThrowWhenGoalDoesNotExistForUser() {
        when(goalRepository.findByIdAndUserId(5L, 8L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> deleteGoal.deleteGoal(5L, 8L)
        );

        assertEquals("Goal not found", exception.getMessage());
        verify(goalRepository, never()).delete(any());
    }
}
