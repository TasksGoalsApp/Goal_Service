package com.GoalService.GoalService.business.Impl;

import com.GoalService.GoalService.domain.Category;
import com.GoalService.GoalService.domain.GetGoalsByUserResponse;
import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.repository.GoalEntity;
import com.GoalService.GoalService.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGoalsByUserImplTest {

    @Mock
    private GoalRepository goalRepository;

    private GetGoalsByUserImpl getGoalsByUser;

    @BeforeEach
    void setUp() {
        getGoalsByUser = new GetGoalsByUserImpl(goalRepository);
    }

    @Test
    void getGoalsByUserShouldConvertAndReturnGoals() {
        long userId = 4L;
        GoalEntity entity = GoalEntity.builder()
                .id(10L)
                .userId(userId)
                .title("Run marathon")
                .description("Train consistently")
                .targetDate(LocalDate.of(2027, 5, 1))
                .progress(35)
                .category(Category.Personal)
                .status(Status.IN_PROGRESS)
                .build();
        when(goalRepository.findGoalsByUserId(userId)).thenReturn(List.of(entity));

        GetGoalsByUserResponse response = getGoalsByUser.getGoalsByUser(userId);

        assertNotNull(response.getGoals());
        assertEquals(1, response.getGoals().size());
        assertEquals(entity.getId(), response.getGoals().getFirst().getId());
        assertEquals(entity.getTitle(), response.getGoals().getFirst().getTitle());
        assertEquals(entity.getStatus(), response.getGoals().getFirst().getStatus());
        verify(goalRepository).findGoalsByUserId(userId);
    }

    @Test
    void getGoalsByUserShouldReturnEmptyListWhenUserHasNoGoals() {
        when(goalRepository.findGoalsByUserId(9L)).thenReturn(List.of());

        GetGoalsByUserResponse response = getGoalsByUser.getGoalsByUser(9L);

        assertNotNull(response.getGoals());
        assertTrue(response.getGoals().isEmpty());
    }
}
