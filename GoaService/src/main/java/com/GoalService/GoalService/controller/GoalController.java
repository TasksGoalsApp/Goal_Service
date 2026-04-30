package com.GoalService.GoalService.controller;

import com.GoalService.GoalService.business.ICreateGoal;
import com.GoalService.GoalService.business.IDeleteGoal;
import com.GoalService.GoalService.business.IGetGoalsByUser;
import com.GoalService.GoalService.business.IUpdateGoal;
import com.GoalService.GoalService.domain.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/updateGoal")
    public ResponseEntity<UpdateGoalResponse> updateGoal(@RequestBody @Valid UpdateGoalRequest updateGoalRequest) {
        UpdateGoalResponse response = updateGoal.updateGoal(updateGoalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetGoalsByUserResponse> getGoalsByUser(@PathVariable long userId) {
        GetGoalsByUserResponse getGoalsByUserResponse = getGoalsByUser.getGoalsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(getGoalsByUserResponse);
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable long goalId) {
        deleteGoal.deleteGoal(goalId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/createGoal")
    public ResponseEntity<CreateGoalResponse> createGoal(@RequestBody @Valid CreateGoalRequest createGoalRequest) {
        CreateGoalResponse response = createGoal.createGoal(createGoalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
