package com.github.martinfrank.maplib;

public class Direction {

    public static final Direction NW = new Direction(-1, -1, "NW");
    public static final Direction N = new Direction(0, -1, "N");
    public static final Direction NE = new Direction(1, -1, "NE");

    public static final Direction W = new Direction(-1, 0, "W");
    public static final Direction C = new Direction(0, 0, "C");
    public static final Direction E = new Direction(1, 0, "E");

    public static final Direction SW = new Direction(-1, 1, "SW");
    public static final Direction S = new Direction(0, 1, "S");
    public static final Direction SE = new Direction(1, 1, "SE");

    public static Direction[] DIRS = {NW, N, NE, W, C, E, SW, S, SE};

    private final int dx;
    private final int dy;
    private final String denomination;

    private Direction(int dx, int dy, String denomination) {
        this.dx = dx;
        this.dy = dy;
        this.denomination = denomination;
    }

    public static Direction byDxDy(int dx, int dy) {
        int tx = Integer.signum(dx);
        int ty = Integer.signum(dy);
        for (Direction dir : DIRS) {
            if (dir.dx == tx && dir.dy == ty) {
                return dir;
            }
        }
        return null;//should never happen
    }

    public int getDx() {
        return dx;
    }

    private int getDy() {
        return dy;
    }

}
