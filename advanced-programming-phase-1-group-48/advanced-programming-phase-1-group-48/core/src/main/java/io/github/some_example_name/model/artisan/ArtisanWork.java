package io.github.some_example_name.model.artisan;

public class ArtisanWork {
    private final String artisanName;      // مثلا "Bee House"
    private final String outputItemName;   // مثلا "Honey"
    private final int readyAtHour;         // ساعت زمانی که آماده میشه
    private final int startDay;            // روز شروع

    public ArtisanWork(String artisanName, String outputItemName, int readyAtHour, int startDay) {
        this.artisanName = artisanName;
        this.outputItemName = outputItemName;
        this.readyAtHour = readyAtHour;
        this.startDay = startDay;
    }

    public boolean isReady(int currentHour, int currentDay) {
        return currentDay > startDay || (currentDay == startDay && currentHour >= readyAtHour);
    }

    public String getArtisanName() {
        return artisanName;
    }

    public String getOutputItemName() {
        return outputItemName;
    }
}
