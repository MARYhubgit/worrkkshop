package io.github.some_example_name.model.building;

public enum BarnType {
    BARN("Barn",
            4,
            "Houses 4 barn-dwelling animals.",
            6_000,   // gold
            350,     // wood
            150,     // stone
            7,       // width
            4,       // height
            new String[]{"Cow" }           // unlocked animals
    ),
    BIG_BARN("bigbarn",
            8,
            "Houses 8 barn-dwelling animals. Unlocks goats.",
            12_000,
            450,
            200,
            7,
            4,
            new String[]{"Goat", "Cow"}
    ),
    DELUXE_BARN("deluxebarn",
            12,
            "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.",
            25_000,
            550,
            300,
            7,
            4,
            new String[]{"Sheep", "Pig", "Cow","Goat"}
    );
    private final String name;
    private final int capacity;
    private final String description;
    private final int goldCost;
    private final int woodCost;
    private final int stoneCost;
    private final int width;
    private final int height;
    private final String[] unlocks;

    BarnType(String name,
        int capacity,
             String description,
             int goldCost,
             int woodCost,
             int stoneCost,
             int width,
             int height,
             String[] unlocks) {
        this.name=name;
        this.capacity = capacity;
        this.description = description;
        this.goldCost = goldCost;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
        this.width = width;
        this.height = height;
        this.unlocks = unlocks;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String[] getUnlocks() {
        return unlocks.clone();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name())
                .append(" [capacity=").append(capacity)
                .append(", cost=").append(goldCost).append("g")
                .append(" + ").append(woodCost).append(" wood")
                .append(" + ").append(stoneCost).append(" stone")
                .append(", size=").append(width).append("x").append(height)
                .append("]");
        if (unlocks.length > 0) {
            sb.append(" Unlocks: ").append(String.join(", ", unlocks));
        }
        return sb.toString();
    }
}
