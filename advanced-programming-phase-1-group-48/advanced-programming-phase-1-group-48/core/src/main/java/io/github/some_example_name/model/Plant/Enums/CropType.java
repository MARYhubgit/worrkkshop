package io.github.some_example_name.model.Plant.Enums;


import io.github.some_example_name.model.Plant.PlantType;
import io.github.some_example_name.model.enums.Season;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum CropType implements PlantType {
    BLUE_JAZZ("BlueJazz", "JazzSeeds", new int[]{1,2,2,2}, 7, true, null, 50, true, 45, 20, Season.SPRING, false),
    CARROT("Carrot", "CarrotSeeds", new int[]{1,1,1}, 3, true, null, 35, true, 75, 33, Season.SPRING, false),
    CAULIFLOWER("Cauliflower", "CauliflowerSeeds", new int[]{1,2,4,4,1}, 12, true, null, 175, true, 75, 33, Season.SPRING, true),
    COFFEE_BEAN("CoffeeBean", "CoffeeBean", new int[]{1,2,2,3,2}, 10, false, 2, 15, false, 0, null, Season.SPRING, false),
    GARLIC("Garlic", "GarlicSeeds", new int[]{1,1,1,1}, 4, true, null, 60, true, 20, 9, Season.SPRING, false),
    GREEN_BEAN("GreenBean", "BeanStarter", new int[]{1,1,1,3,4}, 10, false, 3, 40, true, 25, 11, Season.SPRING, false),
    KALE("Kale", "KaleSeeds", new int[]{1,2,2,1}, 6, true, null, 110, true, 50, 22, Season.SPRING, false),
    PARSNIP("Parsnip", "ParsnipSeeds", new int[]{1,1,1,1}, 4, true, null, 35, true, 25, 11, Season.SPRING, false),
    POTATO("Potato", "PotatoSeeds", new int[]{1,1,1,2,1}, 6, true, null, 80, true, 25, 11, Season.SPRING, false),
    RHUBARB("Rhubarb", "RhubarbSeeds", new int[]{2,2,2,3,4}, 13, true, null, 220, false, 0, null, Season.SPRING, false),
    STRAWBERRY("Strawberry", "StrawberrySeeds", new int[]{1,1,2,2,2}, 8, false, 4, 120, true, 50, 22, Season.SPRING, false),
    TULIP("Tulip", "TulipBulb", new int[]{1,1,2,2}, 6, true, null, 30, true, 45, 20, Season.SPRING, false),
    UNMILLED_RICE("UnmilledRice", "RiceShoot", new int[]{1,2,2,3}, 8, true, null, 30, true, 3, 1, Season.SPRING, false),
    BLUEBERRY("Blueberry", "BlueberrySeeds", new int[]{1,3,3,4,2}, 13, false, 4, 50, true, 25, 11, Season.SUMMER, false),
    CORN("Corn", "CornSeeds", new int[]{2,3,3,3,3}, 14, false, 4, 50, true, 25, 11, Season.SUMMER, false),
    HOPS("Hops", "HopsStarter", new int[]{1,1,2,3,4}, 11, false, 1, 25, true, 45, 20, Season.SUMMER, false),
    HOT_PEPPER("HotPepper", "PepperSeeds", new int[]{1,1,1,1,1}, 5, false, 3, 40, true, 13, 5, Season.SUMMER, false),
    MELON("Melon", "MelonSeeds", new int[]{1,2,3,3,3}, 12, true, null, 250, true, 113, 50, Season.SUMMER, true),
    POPPY("Poppy", "PoppySeeds", new int[]{1,2,2,2}, 7, true, null, 140, true, 45, 20, Season.SUMMER, false),
    RADISH("Radish", "RadishSeeds", new int[]{2,1,2,1}, 6, true, null, 90, true, 45, 20, Season.SUMMER, false),
    RED_CABBAGE("RedCabbage", "RedCabbageSeeds", new int[]{2,1,2,2,2}, 9, true, null, 260, true, 75, 33, Season.SUMMER, false),
    STARFRUIT("Starfruit", "StarfruitSeeds", new int[]{2,3,2,3,3}, 13, true, null, 750, true, 125, 56, Season.SUMMER, false),
    SUMMER_SPANGLE("SummerSpangle", "SpangleSeeds", new int[]{1,2,3,1}, 8, true, null, 90, true, 45, 20, Season.SUMMER, false),
    SUMMER_SQUASH("SummerSquash", "SummerSquashSeeds", new int[]{1,1,1,2,1}, 6, false, 3, 45, true, 63, 28, Season.SUMMER, false),
    SUNFLOWER("Sunflower", "SunflowerSeeds", new int[]{1,2,3,2}, 8, true, null, 80, true, 45, 20, Season.SUMMER, false),
    TOMATO("Tomato", "TomatoSeeds", new int[]{2,2,2,2,3}, 11, false, 4, 60, true, 20, 9, Season.SUMMER, false),
    WHEAT("Wheat", "WheatSeeds", new int[]{1,1,1,1}, 4, true, null, 25, false, 0, null, Season.SUMMER, false),
    AMARANTH("Amaranth", "AmaranthSeeds", new int[]{1,2,2,2}, 7, true, null, 150, true, 50, 22, Season.FALL, false),
    ARTICHOKE("Artichoke", "ArtichokeSeeds", new int[]{2,2,1,2,1}, 8, true, null, 160, true, 30, 13, Season.FALL, false),
    BEET("Beet", "BeetSeeds", new int[]{1,1,2,2}, 6, true, null, 100, true, 30, 13, Season.FALL, false),
    BOK_CHOY("Bok Choy", "BokChoySeeds", new int[]{1,1,1,1}, 4, true, null, 80, true, 25, 11, Season.FALL, false),
    BROCCOLI("Broccoli", "BroccoliSeeds", new int[]{2,2,2,2}, 8, false, 4, 70, true, 63, 28, Season.FALL, false),
    CRANBERRIES("Cranberries", "CranberrySeeds", new int[]{1,2,1,1,2}, 7, false, 5, 75, true, 38, 17, Season.FALL, false),
    EGGPLANT("Eggplant", "EggplantSeeds", new int[]{1,1,1,1}, 5, false, 5, 60, true, 20, 9, Season.FALL, false),
    FAIRY_ROSE("Fairy Rose", "FairySeeds", new int[]{1,4,4,3}, 12, true, null, 290, true, 45, 20, Season.FALL, false),
    GRAPE("Grape", "GrapeStarter", new int[]{1,1,2,3,3}, 10, false, 3, 80, true, 38, 17, Season.FALL, false),
    PUMPKIN("Pumpkin", "PumpkinSeeds", new int[]{1,2,3,4,3}, 13, true, null, 320, false, 0, null, Season.FALL, true),
    YAM("Yam", "YamSeeds", new int[]{1,3,3,3}, 10, true, null, 160, true, 45, 20, Season.FALL, false),
    SWEET_GEM_BERRY("SweetGemBerry", "RareSeed", new int[]{2,4,6,6,6}, 24, true, null, 3000, false, 0, null, Season.FALL, false),
    POWDERMELON("Powdermelon", "PowdermelonSeeds", new int[]{1,2,1,2,1}, 7, true, null, 60, true, 63, 28, Season.WINTER, true),
    ANCIENT_FRUIT("AncientFruit", "AncientSeeds", new int[]{2,7,7,7,5}, 28, false, 7, 550, false, 0, null, Season.SPECIAL, false);

    private final String name;
    private final String source;
    private final int[] stages;
    private final Integer totalHarvestTime;
    private final boolean oneTime;
    private final Integer regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final Integer energy;
    private final Integer baseHealth;
    private final Season season;
    private final boolean canBecomeGiant;

    CropType(String name,
             String source,
             int[] stages,
             Integer totalHarvestTime,
             boolean oneTime,
             Integer regrowthTime,
             int baseSellPrice,
             boolean isEdible,
             Integer energy,
             Integer baseHealth,
             Season season,
             boolean canBecomeGiant) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.baseHealth = baseHealth;
        this.season = season;
        this.canBecomeGiant = canBecomeGiant;
    }

    // getters omitted for brevity

    private static final Map<String, CropType> BY_NAME = new HashMap<>();
    static {
        for (CropType c : values()) {
            BY_NAME.put(c.name.toLowerCase(), c);
        }
    }

    public static Optional<CropType> fromName(String name) {
        return Optional.ofNullable(BY_NAME.get(name.toLowerCase()));
    }

    public String getName() { return name; }

    public Season getSeason() { return season; }

    public int[] getStages() {
        return stages;
    }



    @Override
    public String toString() {
        return String.format(
                "Name: %s%nSource: %s%nStages: %s%nTotal Harvest Time: %d%n" +
                        "One Time: %b%nRegrowth Time: %s%nBase Sell Price: %d%n" +
                        "Is Edible: %b%nBase Energy: %s%nBase Health: %s%n" +
                        "Season: %s%nCan Become Giant: %b",
                name, source, Arrays.toString(stages), totalHarvestTime,
                oneTime, regrowthTime != null ? regrowthTime : "-",
                baseSellPrice, isEdible,
                energy != null ? energy : "-",
                baseHealth != null ? baseHealth : "-",
                season, canBecomeGiant
        );
    }

    public static CropType findByName(String name) {
        for (CropType crop : values()) {
            if (crop.getName().equalsIgnoreCase(name)) {
                return crop;
            }
        }
        return null; // یا می‌تونی Exception بندازی
    }

    public static CropType findBySeedName(String seedName) {
        for (CropType crop : values()) {
            if (crop.getSource().equalsIgnoreCase(seedName)) {
                return crop;
            }
        }
        return null;
    }

    public String getSource() {
        return source;
    }

    public Integer getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public Integer getRegrowthTime() {
        return regrowthTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }
}
