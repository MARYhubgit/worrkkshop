package io.github.some_example_name.model.artisan;

import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.game.Position;

import java.util.HashMap;
import java.util.Map;

public class ArtisanManager {
    private static final Map<Position, ArtisanTask> runningTasks = new HashMap<>();

    public static void registerInProgress(Position pos, String artisanType, String username, int readyHour, String output) {
        runningTasks.put(pos, new ArtisanTask(artisanType, username, readyHour, output));
    }

    public static ArtisanTask getTaskAt(Position pos) {
        return runningTasks.get(pos);
    }

    public static void removeTask(Position pos) {
        runningTasks.remove(pos);
    }

    public static boolean isReady(Position pos) {
        ArtisanTask task = runningTasks.get(pos);
        return task != null && DateAndTime.getHour() >= task.readyHour;
    }
}
