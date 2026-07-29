package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.domain.Category;
import com.GoalService.GoalService.domain.CreateGoalRequest;
import com.GoalService.GoalService.domain.CreateGoalResponse;
import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
@ExtendWith(MockitoExtension.class)
public class CreateGoalImplTest {

    @Mock
    private GoalRepository goalRepository;

    private CreateGoalImpl createGoal;

    @BeforeEach
    void setUp() {
        createGoal = new CreateGoalImpl(goalRepository);
    }

    @Test
    void createGoalShouldSaveGoalAndReturnGeneratedId() {
        long userId = 12L;
        CreateGoalRequest request = CreateGoalRequest.builder()
                .title("Learn Kubernetes")
                .description("Complete the Kubernetes course")
                .targetDate(LocalDate.now().plusDays(30))
                .category(Category.Professional)
                .build();

        when(goalRepository.save(any(GoalEntity.class)))
                .thenAnswer(invocation -> {
                    GoalEntity entity = invocation.getArgument(0);
                    entity.setId(101L);
                    return entity;
                });

        CreateGoalResponse response = createGoal.createGoal(request, userId);

        assertEquals(101L, response.getGoalId());

        ArgumentCaptor<GoalEntity> captor = ArgumentCaptor.forClass(GoalEntity.class);
        verify(goalRepository).save(captor.capture());

        GoalEntity savedGoal = captor.getValue();
        assertEquals(userId, savedGoal.getUserId());
        assertEquals(request.getTitle(), savedGoal.getTitle());
        assertEquals(request.getDescription(), savedGoal.getDescription());
        assertEquals(request.getTargetDate(), savedGoal.getTargetDate());
        assertEquals(request.getCategory(), savedGoal.getCategory());
        assertEquals(0, savedGoal.getProgress());
        assertEquals(Status.NOT_STARTED, savedGoal.getStatus());
    }

    @Test
    void createGoalShouldThrowWhenTargetDateIsNull() {
        CreateGoalRequest request = validRequest();
        request.setTargetDate(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createGoal.createGoal(request, 1L)
        );

        assertEquals("Target date must be in the future", exception.getMessage());
        verifyNoInteractions(goalRepository);
    }

    @Test
    void createGoalShouldThrowWhenTargetDateIsToday() {
        CreateGoalRequest request = validRequest();
        request.setTargetDate(LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> createGoal.createGoal(request, 1L));

        verifyNoInteractions(goalRepository);
    }

    @Test
    void createGoalShouldThrowWhenTargetDateIsInThePast() {
        CreateGoalRequest request = validRequest();
        request.setTargetDate(LocalDate.now().minusDays(1));

        assertThrows(IllegalArgumentException.class, () -> createGoal.createGoal(request, 1L));

        verifyNoInteractions(goalRepository);
    }

    private CreateGoalRequest validRequest() {
        return CreateGoalRequest.builder()
                .title("Goal")
                .description("Description")
                .targetDate(LocalDate.now().plusDays(1))
                .category(Category.Personal)
                .build();
    }

}
