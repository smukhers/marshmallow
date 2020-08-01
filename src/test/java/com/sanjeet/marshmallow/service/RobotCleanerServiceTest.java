package com.sanjeet.marshmallow.service;

import com.sanjeet.marshmallow.model.Point;
import com.sanjeet.marshmallow.model.RequestInstructions;
import com.sanjeet.marshmallow.model.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RobotCleanerServiceTest {

    private final RequestValidator requestValidator = mock(RequestValidator.class);

    private final RobotMover robotMover = mock(RobotMover.class);

    private final RobotCleanerService rcs = new RobotCleanerService(requestValidator, robotMover);

    @Test
    public void validResponseReturnedWithValidInputs() {
        when(robotMover.moveRobot(any(), any(), any())).thenReturn(Arrays.asList(
                new Point(2, 2),
                new Point(2, 3),
                new Point(3, 3),
                new Point(3, 4),
                new Point(4, 4)

        ));

        Response res = rcs.cleanOcean(aValidInput().build());

        assertThat(res.getFinalPosition()).isEqualTo(new int[]{4, 4});
        assertThat(res.getOilPatchesCleaned()).isEqualTo(2L);
    }

    @Test
    public void oilPatchesAreOnlyRemovedOnce() {
        when(robotMover.moveRobot(any(), any(), any())).thenReturn(Arrays.asList(
                new Point(2, 2),
                new Point(2, 3),
                new Point(2, 2),
                new Point(2, 3),
                new Point(2, 4)

        ));

        Response res = rcs.cleanOcean(aValidInput().navigationInstructions("NSNN").build());

        assertThat(res.getFinalPosition()).isEqualTo(new int[]{2, 4});
        assertThat(res.getOilPatchesCleaned()).isEqualTo(2L);
    }

    private RequestInstructions.RequestInstructionsBuilder aValidInput() {
        int[][] oilPatches = {
                new int[]{1, 0},
                new int[]{2, 2},
                new int[]{2, 3}
        };
        return RequestInstructions.builder()
                .areaSize(new int[]{5, 5})
                .startingPosition(new int[]{2, 2})
                .oilPatches(oilPatches)
                .navigationInstructions("NENE");
    }
}