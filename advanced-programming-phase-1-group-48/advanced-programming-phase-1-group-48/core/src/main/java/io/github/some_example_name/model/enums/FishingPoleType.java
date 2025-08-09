package io.github.some_example_name.model.enums;


public enum FishingPoleType {
    TRAINING("Training_Rod", 0, 8, false, 'T'),
    BAMBOO("Bamboo_Rod", 1, 8, true, 'B'),
    FIBERGLASS("Fiberglass_Rod", 2, 6, true, 'F'),
    IRIDIUM("Iridium_Rod", 4, 4, true, 'I');;

    private final String name;
    private final int level;
    private final int baseEnergyCost;
    private final boolean canCatchAllFish;
    private final char displayChar;

    FishingPoleType(String name, int level, int baseEnergyCost, boolean canCatchAllFish, char displayChar) {
        this.name = name;
        this.level = level;
        this.baseEnergyCost = baseEnergyCost;
        this.canCatchAllFish = canCatchAllFish;
        this.displayChar = displayChar;
    }

    public char getDisplayChar() {
        return displayChar;
    }


    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getBaseEnergyCost() { return baseEnergyCost; }
    public boolean canCatchAllFish() { return canCatchAllFish; }
    public static FishingPoleType getNext(FishingPoleType current) {
        for (FishingPoleType type : values()) {
            if (type.level == current.level + 1)
                return type;
        }
        return null;
    }
    public static FishingPoleType fromName(String inputName) {
        for (FishingPoleType type : values()) {
            if (type.getName().equalsIgnoreCase(inputName)) {
                return type;
            }
        }
        return null;
    }

}

