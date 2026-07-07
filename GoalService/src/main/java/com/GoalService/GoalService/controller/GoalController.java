package com.GoalService.GoalService.controller;

import com.GoalService.GoalService.business.*;
import com.GoalService.GoalService.domain.*;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/goal")
@RestController
public class GoalController {

    @Autowired
    private IGetGoalsByUser  getGoalsByUser;

    @Autowired
    private IUpdateGoal  updateGoal;
    @Autowired
    private IDeleteGoal deleteGoal;

    @Autowired
    private ICreateGoal createGoal;

    private IUpdateGoalProgress updateGoalProgress;



    @RolesAllowed({"Customer"})
    @PutMapping("/updateGoal")
    public ResponseEntity<UpdateGoalResponse> updateGoal(@RequestBody @Valid UpdateGoalRequest updateGoalRequest, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        UpdateGoalResponse response = updateGoal.updateGoal(updateGoalRequest, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @RolesAllowed({"Customer"})
    @GetMapping()
    public ResponseEntity<GetGoalsByUserResponse> getGoalsByUser(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        GetGoalsByUserResponse getGoalsByUserResponse = getGoalsByUser.getGoalsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(getGoalsByUserResponse);
    }

    @RolesAllowed({"Customer"})
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable long goalId,  @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        deleteGoal.deleteGoal(goalId, userId);
        return ResponseEntity.noContent().build();
    }
    @RolesAllowed({"Customer"})
    @PostMapping("/createGoal")
    public ResponseEntity<CreateGoalResponse> createGoal(@RequestBody @Valid CreateGoalRequest createGoalRequest, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        CreateGoalResponse response = createGoal.createGoal(createGoalRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RolesAllowed({"Customer"})
    @PatchMapping("/{goalId}/progress")
    public ResponseEntity<UpdateGoalProgressResponse> updateProgress(@RequestBody @Valid UpdateGoalProgressRequest request, @AuthenticationPrincipal Jwt jwt, @PathVariable long goalId ){
        Long userId = jwt.getClaim("id");
        UpdateGoalProgressResponse response = updateGoalProgress.updateGoalProgress(request, userId, goalId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
