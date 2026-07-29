package com.GoalService.GoalService.business.rules;

import com.GoalService.GoalService.domain.Status;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoalStatusResolverTest {

    @ParameterizedTest
    @CsvSource({
            "-1, NOT_STARTED",
            "0, NOT_STARTED",
            "1, IN_PROGRESS",
            "50, IN_PROGRESS",
            "99, IN_PROGRESS",
            "100, COMPLETED",
            "101, COMPLETED"
    })
    void resolveShouldReturnStatusForProgress(int progress, Status expectedStatus) {
        assertEquals(expectedStatus, GoalStatusResolver.resolve(progress));
    }
}
