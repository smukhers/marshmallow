package com.sanjeet.marshmallow.model;

import lombok.Value;

@Value
public class Ocean {
    int width, height;

    public Ocean(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Ocean(int[] areaSize) {
        this(areaSize[0], areaSize[1]);
    }
}
