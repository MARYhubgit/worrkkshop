package io.github.some_example_name.model.artisan;

public class ArtisanTask {
    public final String artisanType;
    public final String username;
    public final int readyHour;
    public final String outputItem;

    public ArtisanTask(String artisanType, String username, int readyHour, String outputItem) {
        this.artisanType = artisanType;
        this.username = username;
        this.readyHour = readyHour;
        this.outputItem = outputItem;
    }
}

