package com.sanjeet.marshmallow.model;

import lombok.Value;

@Value
public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int[] coordinates) {
        this(coordinates[0], coordinates[1]);
    }

    public boolean isInOcean(Ocean ocean) {
        return x >= 0 && x < ocean.getWidth() && y >= 0 && y < ocean.getHeight();
    }

    public int[] toCoordinate() {
        return new int[]{x, y};
    }
}
