package io.github.some_example_name.model.Plant.Enums;

import io.github.some_example_name.model.Plant.PlantType;
import io.github.some_example_name.model.enums.Season;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ForCropType implements PlantType {
    COMMON_MUSHROOM("CommonMushroom", Season.SPECIAL, 40, 38),
    DAFFODIL("Daffodil", Season.SPRING, 30, 0),
    DANDELION("Dandelion", Season.SPRING, 40, 25),
    LEEK("Leek", Season.SPRING, 60, 40),
    MOREL("Morel", Season.SPRING, 150, 20),
    SALMONBERRY("Salmonberry", Season.SPRING, 5, 25),
    SPRING_ONION("SpringOnion", Season.SPRING, 8, 13),
    WILD_HORSERADISH("WildHorseradish", Season.SPRING, 50, 13),
    FIDDLEHEAD_FERN("FiddleheadFern", Season.SUMMER, 90, 25),
    GRAPE("Grape", Season.SUMMER, 80, 38),
    RED_MUSHROOM("RedMushroom", Season.SUMMER, 75, -50),
    SPICE_BERRY("SpiceBerry", Season.SUMMER, 80, 25),
    SWEET_PEA("SweetPea", Season.SUMMER, 50, 0),
    BLACKBERRY("Blackberry", Season.FALL, 25, 25),
    CHANTERELLE("Chanterelle", Season.FALL, 160, 75),
    HAZELNUT("Hazelnut", Season.FALL, 40, 38),
    PURPLE_MUSHROOM("PurpleMushroom", Season.FALL, 90, 30),
    WILD_PLUM("WildPlum", Season.FALL, 80, 25),
    CROCUS("Crocus", Season.WINTER, 60, 0),
    CRYSTAL_FRUIT("Crystal Fruit", Season.WINTER, 150, 63),
    HOLLY("Holly", Season.WINTER, 80, -37),
    SNOW_YAM("SnowYam", Season.WINTER, 100, 30),
    WINTER_ROOT("WinterRoot", Season.WINTER, 70, 25);

    private final String name;
    private final Season season;
    private final int baseSellPrice;
    private final int energyRestored;

    ForCropType(String name, Season season, int baseSellPrice, int energyRestored) {
        this.name = name;
        this.season = season;
        this.baseSellPrice = baseSellPrice;
        this.energyRestored = energyRestored;
    }

    // یک lookup ساده بر اساس نام:
    private static final Map<String, ForCropType> BY_NAME = new HashMap<>();
    static {
        for (ForCropType c : values()) {
            BY_NAME.put(c.name.toLowerCase(), c);
        }
    }

    public static Optional<ForCropType> fromName(String name) {
        return Optional.ofNullable(BY_NAME.get(name.toLowerCase()));
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s%nSeason: %s%nBase Sell Price: %d%nEnergy Restored: %d",
                name, season, baseSellPrice, energyRestored
        );
    }

    @Override
    public String getName() {
        return this.name;
    }
}
