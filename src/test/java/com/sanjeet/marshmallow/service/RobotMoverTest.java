package com.sanjeet.marshmallow.service;

import com.sanjeet.marshmallow.error.RobotAgroundException;
import com.sanjeet.marshmallow.model.Direction;
import com.sanjeet.marshmallow.model.Ocean;
import com.sanjeet.marshmallow.model.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RobotMoverTest {

    private final RobotMover rm = new RobotMover();

    @Test
    public void validInputsPass() {
        assertThat(rm.moveRobot(anOcean(), aPoint(), generateDirections("NESW")))
                .containsExactly(
                        new Point(1, 2),
                        new Point(1, 3),
                        new Point(2, 3),
                        new Point(2, 2),
                        new Point(1, 2)
                );
    }

    @Test
    public void robotRunsAgroundBeyondOceanBounds() {
        assertThatThrownBy(() -> rm.moveRobot(anOcean(), aPoint(), generateDirections("WWW")))
                .isInstanceOf(RobotAgroundException.class)
                .hasMessageContaining("is not within Ocean");
    }

    private Ocean anOcean() {
        return new Ocean(5, 5);
    }

    private Point aPoint() {
        return new Point(1, 2);
    }

    private List<Direction> generateDirections(String d) {
        List<Direction> directions = new ArrayList<>();
        for (char ch : d.toCharArray()) {
            directions.add(Direction.valueOf(String.valueOf(ch)));
        }
        return directions;
    }
}