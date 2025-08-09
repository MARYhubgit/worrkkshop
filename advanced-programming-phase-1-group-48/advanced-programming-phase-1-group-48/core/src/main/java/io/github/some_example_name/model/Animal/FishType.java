package io.github.some_example_name.model.Animal;


import io.github.some_example_name.model.enums.Season;

public enum FishType {
    SALMON("Salmon", 75, Season.FALL,false),
    SARDINE("Sardine", 40, Season.FALL,false),
    SHAD("Shad", 60, Season.FALL,false),
    BLUE_DISCUS("Blue Discus", 120, Season.FALL,false),
    MIDNIGHT_CARP("Midnight Carp", 150, Season.WINTER,false),
    SQUID("Squid", 80, Season.WINTER,false),
    TUNA("Tuna", 100, Season.WINTER,false),
    PERCH("Perch", 55, Season.WINTER,false),
    FLOUNDER("Flounder", 100, Season.SPRING,false),
    LIONFISH("Lionfish", 100, Season.SPRING,false),
    HERRING("Herring", 30, Season.SPRING,false),
    GHOSTFISH("Ghostfish", 45, Season.SPRING,false),
    TILAPIA("Tilapia", 75, Season.SUMMER,false),
    DORADO("Dorado", 100, Season.SUMMER,false),
    SUNFISH("Sunfish", 30, Season.SUMMER,false),
    RAINBOW_TROUT("Rainbow Trout", 65, Season.SUMMER,false),
    LEGEND("Legend", 5000, Season.SPRING, true),
    GLACIERFISH("Glacierfish", 1000, Season.WINTER, true),
    ANGLER("Angler", 900, Season.FALL, true),
    CRIMSONFISH("Crimsonfish", 1500, Season.SUMMER, true);



    private final String displayName;
    private final int basePrice;
    private final Season seasonAvailable;
    private final boolean isLegendary;

    FishType(String displayName, int basePrice, Season seasonAvailable, boolean isLegendary) {
        this.displayName = displayName;
        this.basePrice = basePrice;
        this.seasonAvailable = seasonAvailable;
        this.isLegendary = isLegendary;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public Season getSeasonAvailable() {
        return seasonAvailable;
    }

    public boolean isLegendary() {
        return isLegendary;
    }
}


