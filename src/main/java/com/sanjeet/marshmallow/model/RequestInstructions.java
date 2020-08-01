package com.sanjeet.marshmallow.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestInstructions {
    int[] areaSize;
    int[] startingPosition;
    int[][] oilPatches;
    String navigationInstructions;
}
