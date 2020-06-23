package com.github.martinfrank.maplib;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum MapStyle {

    HEX_VERTICAL(Direction.NW, Direction.NE, Direction.E, Direction.SE, Direction.SW, Direction.W),
    HEX_HORIZONTAL(Direction.NW, Direction.N, Direction.NE, Direction.SE, Direction.S, Direction.SW),

    SQUARE4(Direction.N, Direction.E, Direction.S, Direction.W),
    SQUARE8(Direction.NW, Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SW, Direction.W),

    SQUARE_DIAMOND4(Direction.NW, Direction.NE, Direction.SE, Direction.SW),
    SQUARE_ISOMETRIC4(Direction.NW, Direction.NE, Direction.SE, Direction.SW),

    TRIANGLE_VERTICAL(Direction.NW, Direction.NE, Direction.E, Direction.SE, Direction.SW, Direction.W),
    TRIANGLE_HORIZONTAL(Direction.NW, Direction.N, Direction.NE, Direction.SE, Direction.S, Direction.SW);


    private final Set<Direction> directions;

    MapStyle(Direction... directions) {
        this.directions = new HashSet<>(Arrays.asList(directions));
    }

    public Set<Direction> getDirections() {
        return directions;
    }

}
