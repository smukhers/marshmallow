package com.sanjeet.marshmallow.service;

import com.sanjeet.marshmallow.model.Direction;
import com.sanjeet.marshmallow.model.Ocean;
import com.sanjeet.marshmallow.model.Point;
import com.sanjeet.marshmallow.model.RequestInstructions;
import com.sanjeet.marshmallow.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RobotCleanerService {

    private final RequestValidator requestValidator;

    private final RobotMover robotMover;

    @Autowired
    public RobotCleanerService(RequestValidator requestValidator, RobotMover robotMover) {
        this.requestValidator = requestValidator;
        this.robotMover = robotMover;
    }

    public Response cleanOcean(RequestInstructions requestInstructions) {
        requestValidator.validateInput(requestInstructions);

        Ocean ocean = new Ocean(requestInstructions.getAreaSize());
        Point startingPoint = new Point(requestInstructions.getStartingPosition());
        Set<Point> oilPatches = parseOilPatches(requestInstructions);
        List<Direction> directions = parseDirections(requestInstructions);

        List<Point> robotPath = robotMover.moveRobot(ocean, startingPoint, directions);

        long oilPatchesCleaned = findNumberOfCleanedPatches(oilPatches, robotPath);

        return new Response(oilPatchesCleaned, robotPath.get(robotPath.size() - 1).toCoordinate());
    }

    private Set<Point> parseOilPatches(RequestInstructions requestInstructions) {
        Set<Point> oilPatches = new HashSet<>();
        if (requestInstructions.getOilPatches() != null) {
            for (int[] oilPatch : requestInstructions.getOilPatches()) {
                oilPatches.add(new Point(oilPatch));
            }
        }
        return oilPatches;
    }

    private List<Direction> parseDirections(RequestInstructions requestInstructions) {
        List<Direction> directions = new ArrayList<>();
        for (char ch : requestInstructions.getNavigationInstructions().toCharArray()) {
            directions.add(Direction.valueOf(String.valueOf(ch)));
        }
        return directions;
    }

    private long findNumberOfCleanedPatches(Set<Point> oilPatches, List<Point> robotPath) {
        Set<Point> robotPoints = new HashSet<>(robotPath);
        return oilPatches.stream()
                .filter(robotPoints::contains)
                .count();
    }

}
