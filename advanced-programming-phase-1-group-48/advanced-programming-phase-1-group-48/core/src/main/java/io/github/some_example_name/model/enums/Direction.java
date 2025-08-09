package io.github.some_example_name.model.enums;

public enum Direction {
    N(0, -1), NE(1, -1), E(1, 0), SE(1, 1),
    S(0, 1), SW(-1, 1), W(-1, 0), NW(-1, -1);

    public final int dx, dy;
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction fromString(String input) {
        try {
            return Direction.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
