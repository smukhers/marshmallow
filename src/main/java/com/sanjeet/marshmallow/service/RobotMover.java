package com.sanjeet.marshmallow.service;

import com.sanjeet.marshmallow.error.RobotAgroundException;
import com.sanjeet.marshmallow.model.Direction;
import com.sanjeet.marshmallow.model.Ocean;
import com.sanjeet.marshmallow.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotMover {

    public List<Point> moveRobot(Ocean ocean, Point currentPoint, List<Direction> directions) {
        validatePosition(currentPoint, ocean);

        List<Point> travelPath = new ArrayList<>();
        travelPath.add(currentPoint);

        for (Direction d : directions) {
            currentPoint = moveRobot(currentPoint, d);
            validatePosition(currentPoint, ocean);
            travelPath.add(currentPoint);
        }

        return travelPath;
    }

    private Point moveRobot(Point currentPoint, Direction direction) {
        Point nextPoint;
        switch (direction) {
            case N:
                nextPoint = new Point(currentPoint.getX(), currentPoint.getY() + 1);
                break;
            case E:
                nextPoint = new Point(currentPoint.getX() + 1, currentPoint.getY());
                break;
            case S:
                nextPoint = new Point(currentPoint.getX(), currentPoint.getY() - 1);
                break;
            case W:
                nextPoint = new Point(currentPoint.getX() - 1, currentPoint.getY());
                break;
            default:
                return currentPoint;
        }

        return nextPoint;
    }

    private void validatePosition(Point currentPoint, Ocean ocean) {
        if (!currentPoint.isInOcean(ocean)) {
            throw new RobotAgroundException(String.format("%s is not within %s", currentPoint, ocean));
        }
    }

}
