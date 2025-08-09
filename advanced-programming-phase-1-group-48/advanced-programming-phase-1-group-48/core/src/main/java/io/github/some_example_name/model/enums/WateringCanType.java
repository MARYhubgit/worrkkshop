package io.github.some_example_name.model.enums;

public enum WateringCanType {
    BASIC("Basic_Watering_Can", 'B', 0, 40, 5),
    COPPER("Copper_Watering_Can", 'C', 1, 55, 4),
    IRON("Iron_Watering_Can", 'I', 2, 70, 3),
    GOLD("Gold_Watering_Can", 'G', 3, 85, 2),
    IRIDIUM("Iridium_Watering_Can", 'D', 4, 100, 1);

    private final String name;
    private final char displayChar;
    private final int level;
    private final int capacity;
    private final int baseEnergyCost;

    WateringCanType(String name, char displayChar, int level, int capacity, int baseEnergyCost) {
        this.name = name;
        this.displayChar = displayChar;
        this.level = level;
        this.capacity = capacity;
        this.baseEnergyCost = baseEnergyCost;
    }

    public String getName() { return name; }
    public char getDisplayChar() { return displayChar; }
    public int getLevel() { return level; }
    public int getCapacity() { return capacity; }
    public int getBaseEnergyCost() { return baseEnergyCost; }
    public static WateringCanType getNext(WateringCanType current) {
        for (WateringCanType type : values()) {
            if (type.level == current.level + 1)
                return type;
        }
        return null;
    }
}
