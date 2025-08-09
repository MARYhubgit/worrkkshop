package io.github.some_example_name.model.enums;


public enum AxeType {
    BASIC("Basic_Axe",'B',0, 5),
    COPPER("Copper_Axe",'C', 1, 4),
    IRON("Iron_Axe", 'I',2, 3),
    GOLD("Gold_Axe", 'G',3, 2),
    IRIDIUM("Iridium_Axe", 'D',4, 1);

    private final char displayChar;
    private final String name;
    private final int level;
    private final int baseEnergyCost;

    AxeType(String name,char displayChar, int level, int baseEnergyCost) {
        this.displayChar = displayChar;
        this.name = name;
        this.level = level;
        this.baseEnergyCost = baseEnergyCost;
    }
    public static AxeType getNext(AxeType current) {
        for (AxeType type : values()) {
            if (type.level == current.level + 1)
                return type;
        }
        return null;
    }



    public String getName() { return name; }

    public char getDisplayChar() {
        return displayChar;
    }
    public int getLevel() { return level; }
    public int getBaseEnergyCost() { return baseEnergyCost; }
}

