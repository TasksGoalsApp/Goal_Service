package com.GoalService.GoalService.controller;

import com.GoalService.GoalService.business.ICreateGoal;
import com.GoalService.GoalService.business.IDeleteGoal;
import com.GoalService.GoalService.business.IGetGoalsByUser;
import com.GoalService.GoalService.business.IUpdateGoal;
import com.GoalService.GoalService.business.IUpdateGoalProgress;
import com.GoalService.GoalService.domain.Category;
import com.GoalService.GoalService.domain.CreateGoalResponse;
import com.GoalService.GoalService.domain.GetGoalsByUserResponse;
import com.GoalService.GoalService.domain.Goal;
import com.GoalService.GoalService.domain.Status;
import com.GoalService.GoalService.domain.UpdateGoalProgressResponse;
import com.GoalService.GoalService.domain.UpdateGoalResponse;
import com.GoalService.GoalService.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GoalController.class)
public class GoalControllerTest {

    private static final long USER_ID = 42L;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IGetGoalsByUser getGoalsByUser;

    @MockitoBean
    private IUpdateGoal updateGoal;

    @MockitoBean
    private IDeleteGoal deleteGoal;

    @MockitoBean
    private ICreateGoal createGoal;

    @MockitoBean
    private IUpdateGoalProgress updateGoalProgress;


    @Test
    void createGoalShouldReturnCreatedResponse() throws Exception {
        when(createGoal.createGoal(any(), eq(USER_ID)))
                .thenReturn(CreateGoalResponse.builder().goalId(10L).build());

        mockMvc.perform(post("/goal/createGoal")
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Learn Kubernetes",
                                  "description": "Complete a practical Kubernetes course",
                                  "targetDate": "2030-12-31",
                                  "category": "Professional"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.goalId").value(10));

        verify(createGoal).createGoal(any(), eq(USER_ID));
    }

    @Test
    void createGoalShouldReturnBadRequestWhenTitleIsBlank() throws Exception {
        mockMvc.perform(post("/goal/createGoal")
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "",
                                  "description": "Complete a practical Kubernetes course",
                                  "targetDate": "2030-12-31",
                                  "category": "Professional"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("title")));

        verifyNoInteractions(createGoal);
    }

    @Test
    void getGoalsByUserShouldReturnGoals() throws Exception {
        Goal goal = Goal.builder()
                .id(10L)
                .userId(USER_ID)
                .title("Learn Kubernetes")
                .description("Complete a practical Kubernetes course")
                .targetDate(LocalDate.of(2030, 12, 31))
                .progress(25)
                .category(Category.Professional)
                .status(Status.IN_PROGRESS)
                .build();

        when(getGoalsByUser.getGoalsByUser(USER_ID))
                .thenReturn(GetGoalsByUserResponse.builder().goals(List.of(goal)).build());

        mockMvc.perform(get("/goal")
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goals[0].id").value(10))
                .andExpect(jsonPath("$.goals[0].userId").value(USER_ID))
                .andExpect(jsonPath("$.goals[0].title").value("Learn Kubernetes"))
                .andExpect(jsonPath("$.goals[0].progress").value(25))
                .andExpect(jsonPath("$.goals[0].category").value("Professional"))
                .andExpect(jsonPath("$.goals[0].status").value("IN_PROGRESS"));

        verify(getGoalsByUser).getGoalsByUser(USER_ID);
    }

    @Test
    void updateGoalShouldReturnUpdatedGoal() throws Exception {
        UpdateGoalResponse response = UpdateGoalResponse.builder()
                .id(10L)
                .userId(USER_ID)
                .title("Updated goal")
                .description("Updated description")
                .targetDate(LocalDate.of(2031, 1, 15))
                .progress(50)
                .category(Category.Personal)
                .status(Status.IN_PROGRESS)
                .build();

        when(updateGoal.updateGoal(any(), eq(USER_ID))).thenReturn(response);

        mockMvc.perform(put("/goal/updateGoal")
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 10,
                                  "title": "Updated goal",
                                  "description": "Updated description",
                                  "targetDate": "2031-01-15",
                                  "progress": 50,
                                  "category": "Personal",
                                  "status": "IN_PROGRESS"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.userId").value(USER_ID))
                .andExpect(jsonPath("$.title").value("Updated goal"))
                .andExpect(jsonPath("$.progress").value(50));

        verify(updateGoal).updateGoal(any(), eq(USER_ID));
    }

    @Test
    void updateGoalShouldReturnBadRequestWhenProgressIsAboveOneHundred() throws Exception {
        mockMvc.perform(put("/goal/updateGoal")
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 10,
                                  "title": "Updated goal",
                                  "description": "Updated description",
                                  "targetDate": "2031-01-15",
                                  "progress": 101,
                                  "category": "Personal",
                                  "status": "IN_PROGRESS"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("progress")));

        verifyNoInteractions(updateGoal);
    }

    @Test
    void deleteGoalShouldReturnNoContent() throws Exception {
        doNothing().when(deleteGoal).deleteGoal(10L, USER_ID);

        mockMvc.perform(delete("/goal/{goalId}", 10L)
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER"))))
                .andExpect(status().isNoContent());

        verify(deleteGoal).deleteGoal(10L, USER_ID);
    }

    @Test
    void updateProgressShouldReturnUpdatedProgress() throws Exception {
        when(updateGoalProgress.updateGoalProgress(any(), eq(USER_ID), eq(10L)))
                .thenReturn(UpdateGoalProgressResponse.builder().progress(75).build());

        mockMvc.perform(patch("/goal/{goalId}/progress", 10L)
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "progress": 75
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.progress").value(75));

        verify(updateGoalProgress).updateGoalProgress(any(), eq(USER_ID), eq(10L));
    }

    @Test
    void updateProgressShouldReturnBadRequestWhenProgressIsNegative() throws Exception {
        mockMvc.perform(patch("/goal/{goalId}/progress", 10L)
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "progress": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("progress")));

        verifyNoInteractions(updateGoalProgress);
    }

    @Test
    void deleteGoalShouldReturnNotFoundWhenServiceThrowsResourceNotFound() throws Exception {
        org.mockito.Mockito.doThrow(new ResourceNotFoundException("Goal not found"))
                .when(deleteGoal).deleteGoal(999L, USER_ID);

        mockMvc.perform(delete("/goal/{goalId}", 999L)
                        .with(jwt()
                                .jwt(token -> token.claim("id", USER_ID))
                                .authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Goal not found"));
    }
}
