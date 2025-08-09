package io.github.some_example_name.model.Plant.Enums;

import io.github.some_example_name.model.Plant.PlantType;
import io.github.some_example_name.model.enums.Season;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TreeType implements PlantType {
    APRICOT_TREE("ApricotTree","ApricotSapling",new int[]{7,7,7,7},28,"Apricot",1,59,true,38,Season.SPRING),
    CHERRY_TREE("CherryTree","CherrySapling",new int[]{7,7,7,7},28,"Cherry",1,80,true,38,Season.SPRING),
    BANANA_TREE("BananaTree","BananaSapling",new int[]{7,7,7,7},28,"Banana",1,150,true,75,Season.SUMMER),
    MANGO_TREE("MangoTree","MangoSapling",new int[]{7,7,7,7},28,"Mango",1,130,true,100,Season.SUMMER),
    ORANGE_TREE("OrangeTree","OrangeSapling",new int[]{7,7,7,7},28,"Orange",1,100,true,38,Season.SUMMER),
    PEACH_TREE("PeachTree","PeachSapling",new int[]{7,7,7,7},28,"Peach",1,140,true,38,Season.SUMMER),
    APPLE_TREE("AppleTree","AppleSapling",new int[]{7,7,7,7},28,"Apple",1,100,true,38,Season.FALL),
    POMEGRANATE_TREE("PomegranateTree","Pomegranate Sapling",new int[]{7,7,7,7},28,"Pomegranate",1,140,true,38,Season.FALL),
    OAK_TREE("OakTree","Acorns",new int[]{7,7,7,7},28,"Oak Resin",7,150,false,-1,Season.SPECIAL),
    MAPLE_TREE("MapleTree","MapleSeeds",new int[]{7,7,7,7},28,"Maple Syrup",9,200,false,-1,Season.SPECIAL),
    PINE_TREE("PineTree","PineCones",new int[]{7,7,7,7},28,"Pine Tar",5,100,false,-1,Season.SPECIAL),
    MAHOGANY_TREE("MahoganyTree","MahoganySeeds",new int[]{7,7,7,7},28,"Sap",1,2,true,-2,Season.SPECIAL),
    MUSHROOM_TREE("MushroomTree","MushroomTreeSeeds",new int[]{7,7,7,7},28,"Common Mushroom",1,40,true,38,Season.SPECIAL),
    MYSTIC_TREE("MysticTree","MysticTreeSeeds",new int[]{7,7,7,7},28,"Mystic Syrup",7,1000,true,500,Season.SPECIAL);

    private final String name;
    private final String seedSource;
    private final int[] stages;
    private final int totalTime;
    private final String fruit;
    private final int fruitCycle;
    private final int fruitBasePrice;
    private final boolean isEdible;
    private final int fruitEnergy;
    private final Season fruitSeason;

    TreeType(String name, String seedSource, int[] stages, int totalTime, String fruit, int fruitCycle,
             int fruitBasePrice, boolean isEdible, int fruitEnergy, Season fruitSeason) {
        this.name = name;
        this.seedSource = seedSource;
        this.stages = stages;
        this.totalTime = totalTime;
        this.fruit = fruit;
        this.fruitCycle = fruitCycle;
        this.fruitBasePrice = fruitBasePrice;
        this.isEdible = isEdible;
        this.fruitEnergy = fruitEnergy;
        this.fruitSeason = fruitSeason;
    }

    @Override public String getName() { return name; }

    public int[] getStages() { return stages; }
    public String getFruit() { return fruit; }
    public int getFruitCycle() { return fruitCycle; }
    public int getFruitBasePrice() { return fruitBasePrice; }
    public String getSeedSource() {
        return seedSource;
    }

    // lookup map
    private static final Map<String, TreeType> BY_NAME = new HashMap<>();
    static { for (TreeType t : values()) BY_NAME.put(t.name.toLowerCase(), t); }
    public static Optional<TreeType> fromName(String name) {
        return name == null ? Optional.empty() : Optional.ofNullable(BY_NAME.get(name.toLowerCase()));
    }

    @Override public String toString() {
        return String.format(
                "Name: %s%nSource: %s%nStages: %s%nTotal Time: %d%n" +
                        "Fruit: %s%nFruit Cycle: %d%nBase Sell Price: %d%n" +
                        "Is Edible: %b%nFruit Energy: %d%nSeason: %s",
                name, seedSource, java.util.Arrays.toString(stages), totalTime,
                fruit, fruitCycle, fruitBasePrice,
                isEdible, fruitEnergy, fruitSeason
        );
    }

    public int getTotalTime() {
        return totalTime;
    }

    public static TreeType findBySeedName(String seedName) {
        for (TreeType crop : values()) {
            if (crop.getSeedSource().equalsIgnoreCase(seedName)) {
                return crop;
            }
        }
        return null;
    }
}
