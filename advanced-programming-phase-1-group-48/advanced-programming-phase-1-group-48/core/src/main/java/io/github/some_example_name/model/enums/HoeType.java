package io.github.some_example_name.model.enums;

public enum HoeType {
    BASIC("Basic_Hoe", 'B', 0, 5),
    COPPER("Copper_Hoe", 'C', 1, 4),
    IRON("Iron_Hoe", 'I', 2, 3),
    GOLD("Gold_Hoe", 'G', 3, 2),
    IRIDIUM("Iridium_Hoe", 'D', 4, 1);

    private final String name;
    private final char displayChar;
    private final int level;
    private final int baseEnergyCost;

    HoeType(String name, char displayChar, int level, int baseEnergyCost) {
        this.name = name;
        this.displayChar = displayChar;
        this.level = level;
        this.baseEnergyCost = baseEnergyCost;
    }

    public String getName() {
        return name;
    }

    public char getDisplayChar() {
        return displayChar;
    }

    public int getLevel() {
        return level;
    }

    public int getBaseEnergyCost() {
        return baseEnergyCost;
    }

    public static HoeType getNext(HoeType current) {
        for (HoeType type : values()) {
            if (type.level == current.level + 1)
                return type;
        }
        return null;
    }
}
