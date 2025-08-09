package io.github.some_example_name.model.enums;

import java.util.EnumSet;
import java.util.Set;


public enum WeatherType {
    SUNNY(EnumSet.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER)),
    RAINY(EnumSet.of(Season.SPRING, Season.SUMMER, Season.FALL)),
    STORM(EnumSet.of(Season.SPRING, Season.SUMMER, Season.FALL)),
    SNOW(EnumSet.of(Season.WINTER));

    private final Set<Season> validSeasons;

    WeatherType(Set<Season> validSeasons) {
        this.validSeasons = validSeasons;
    }

    public boolean isValidForSeason(Season season) {
        return validSeasons.contains(season);
    }


    public Set<Season> getValidSeasons() {
        return EnumSet.copyOf(validSeasons);
    }
}

