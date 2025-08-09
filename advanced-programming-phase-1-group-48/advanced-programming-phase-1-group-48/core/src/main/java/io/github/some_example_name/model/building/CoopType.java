package io.github.some_example_name.model.building;

public enum CoopType {
    COOP("coop",
            4,
            "Houses 4 coop-dwelling animals.",
            4_000,    // gold
            300,      // wood
            100,      // stone
            6,        // width
            3,        // height
            new String[]{"Chicken"}          // unlocked animals
    ),
    LARGE_COOP("largecoop",
            8,
            "Houses 8 coop-dwelling animals. Unlocks ducks.",
            10_000,
            400,
            150,
            6,
            3,
            new String[]{"Duck","Chicken","Dinosaur"}
    ),
    DELUXE_COOP("deluxecoop",
            12,
            "Houses 12 coop-dwelling animals. Unlocks rabbits.",
            20_000,
            500,
            200,
            6,
            3,
            new String[]{"Rabbit","Chicken","Duck"}
    );

    private String name;
    private final int capacity;
    private final String description;
    private final int goldCost;
    private final int woodCost;
    private final int stoneCost;
    private final int width;
    private final int height;
    private final String[] unlocks;

    CoopType(String name,int capacity,
             String description,
             int goldCost,
             int woodCost,
             int stoneCost,
             int width,
             int height,
             String[] unlocks) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    /**
     * @return list of animal types unlocked by building this coop (e.g. "Duck", "Rabbit"), or empty if none.
     */
    public String[] getUnlocks() {
        return unlocks.clone();
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
