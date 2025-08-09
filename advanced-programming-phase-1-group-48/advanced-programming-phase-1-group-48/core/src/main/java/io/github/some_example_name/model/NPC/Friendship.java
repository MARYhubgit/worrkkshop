package io.github.some_example_name.model.NPC;

public class Friendship {
    private static final int MAX_POINTS = 799;

    public static int getLevel(int points) {
        return Math.min(points / 200, 3);
    }

    public static int getPointsForNextLevel(int currentPoints) {
        int currentLevel = getLevel(currentPoints);
        return Math.min((currentLevel + 1) * 200, MAX_POINTS);
    }
}

