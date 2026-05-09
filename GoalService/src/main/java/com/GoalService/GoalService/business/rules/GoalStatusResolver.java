package com.GoalService.GoalService.business.rules;

import com.GoalService.GoalService.domain.Status;

public final class GoalStatusResolver {

    private GoalStatusResolver() {
    }

    public static Status resolve(int progress) {
        if (progress <= 0) {
            return Status.NOT_STARTED;
        }

        if (progress >= 100) {
            return Status.COMPLETED;
        }

        return Status.IN_PROGRESS;
    }
}
