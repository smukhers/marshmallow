package com.sanjeet.marshmallow.service;

import com.sanjeet.marshmallow.error.ParameterParsingException;
import com.sanjeet.marshmallow.model.RequestInstructions;
import org.springframework.stereotype.Service;

@Service
public class RequestValidator {

    public void validateInput(RequestInstructions requestInstructions) {
        validateSize2Array(requestInstructions.getAreaSize(), "areaSize");
        validateSize2Array(requestInstructions.getStartingPosition(), "startingPosition");

        int[] areaSize = requestInstructions.getAreaSize();
        int oceanSize = areaSize[0] * areaSize[1];
        validateOilPatches(requestInstructions.getOilPatches(), oceanSize);

        validateNavigationInstructions(requestInstructions.getNavigationInstructions());
    }

    private void validateSize2Array(int[] input, String fieldName) {
        if (input == null || input.length != 2) {
            throw new ParameterParsingException(String.format("%s is required in format [int, int].", fieldName));
        }
    }

    private void validateOilPatches(int[][] oilPatches, int oceanSize) {
        if (oilPatches == null || oilPatches.length == 0) return;

        for (int[] oilPatch : oilPatches) {
            validateSize2Array(oilPatch, "Each oilPatch in oilPatches[]");
        }


        if (oilPatches.length > oceanSize) {
            throw new ParameterParsingException(String.format("Too many oil patches for ocean of size %d", oceanSize));
        }
    }

    private void validateNavigationInstructions(String navigationInstructions) {
        if (navigationInstructions == null || navigationInstructions.isEmpty()) {
            throw new ParameterParsingException("navigationInstructions is required as String sequence of cardinal directions [NESW]");
        }

        if (!navigationInstructions.replaceAll("[NESW]", "").isEmpty()) {
            throw new ParameterParsingException("navigationInstructions contains invalid cardinal directions");
        }
    }
}
