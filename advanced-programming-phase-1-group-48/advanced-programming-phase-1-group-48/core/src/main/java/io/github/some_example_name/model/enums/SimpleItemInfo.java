package io.github.some_example_name.model.enums;

public enum SimpleItemInfo {
    TROUT_SOUP("Trout_Soup",'t',100,80,true,"Food"),
    OIL("Oil" , 'o',100 , 80,true,"Ingredient"),
    EGG("Egg", 'E', 50, 30, true, "Ingredient"),
    MILK("Milk", 'M', 60, 35, true, "Ingredient"),
    SARDINE("Sardine", 'S', 90, 60, true, "Fish"),
    SALMON("Salmon", 'S', 120, 80, true, "Fish"),
    LEEK("Leek", 'L', 45, 25, true, "Vegetable"),
    DANDELION("Dandelion", 'D', 15, 8, true, "Forage"),
    COFFEE("Coffee", 'C', 100, 70, true, "Drink"),
    SUGAR("Sugar", 'S', 40, 25, true, "Ingredient"),
    RICE("Rice", 'R', 30, 20, true, "Crop"),
    FIBER("Fiber", 'F', 10, 5, true, "Resource"),
    CHEESE("Cheese", 'C', 120, 90, true, "Dairy"),
    CABBAGE("Cabbage", 'C', 100, 70, true, "Vegetable"),
    FLOUNDER("Flounder", 'F', 100, 70, true, "Fish"),
    MIDNIGHT_CARP("MidnightCarp", 'M', 110, 75, true, "Fish"),
    APRICOT("Apricot", 'A', 59, 38, true, "fruit"),
    CHERRY("Cherry", 'C', 80, 38, true, "fruit"),
    BANANA("Banana", 'B', 150, 75, true, "fruit"),
    MANGO("Mango", 'M', 130, 100, true, "fruit"),
    ORANGE("Orange", 'O', 100, 38, true, "fruit"),
    PEACH("Peach", 'P', 140, 38, true, "fruit"),
    APPLE("Apple", 'A', 100, 38, true, "fruit"),
    POMEGRANATE("Pomegranate", 'G', 140, 38, true, "fruit"),
    //حلقه ازدواج
    WEDDING_RING("Wedding Ring", 'W', 10000, 0, false, "ring"),

    CHERRY_BOMB("Cherry Bomb", 'C', 50, 0, false, "bomb"),
    BOMB("Bomb", 'B', 50, 0, false, "bomb"),
    MEGA_BOMB("Mega Bomb", 'M', 50, 0, false, "bomb"),

    SPRINKLER("Sprinkler", 'S', 0, 0, false, "machine"),
    QUALITY_SPRINKLER("Quality Sprinkler", 'Q', 0, 0, false, "machine"),
    IRIDIUM_SPRINKLER("Iridium Sprinkler", 'I', 0, 0, false, "machine"),

    CHARCOAL_KILN("Charcoal Klin", 'K', 0, 0, false, "machine"),
    FURNACE("Furnace", 'F', 0, 0, false, "machine"),
    SCARECROW("Scarecrow", 'S', 0, 0, false, "decoration"),
    DELUXE_SCARECROW("Deluxe Scarecrow", 'D', 0, 0, false, "decoration"),

    BEE_HOUSE("Bee_House", 'H', 0, 0, false, "machine"),
    CHEESE_PRESS("Cheese_Press", 'C', 0, 0, false, "machine"),
    KEG("Keg", 'K', 0, 0, false, "machine"),
    LOOM("Loom", 'L', 0, 0, false, "machine"),
    MAYONNAISE_MACHINE("Mayonnaise_Machine", 'M', 0, 0, false, "machine"),
    OIL_MAKER("Oil_Maker", 'O', 0, 0, false, "machine"),
    PRESERVES_JAR("Preserves Jar", 'P', 0, 0, false, "machine"),

    DEHYDRATOR("Dehydrator", 'D', 0, 0, false, "machine"),
    GRASS_STARTER("Grass_Starter", 'G', 0, 0, false, "seed"),
    FISH_SMOKER("Fish_Smoker", 'F', 0, 0, false, "machine"),

    MYSTIC_TREE_SEED("Mystic_Tree_Seed", 'T', 100, 0, false, "seed"),

    OAK_RESIN("Oak_Resin", 'R', 150, 0, false, "resin"),
    MAPLE_SYRUP("Maple_Syrup", 'S', 200, 0, false, "syrup"),
    PINE_TAR("Pine_Tar", 'T', 100, 0, false, "tar"),
    SAP("Sap", 's', 2, -2, true, "sap"),
    COMMON_MUSHROOM("Common Mushroom", 'm', 40, 38, true, "fungus"),
    MYSTIC_SYRUP("Mystic Syrup", 'Y', 1000, 500, true, "syrup"),
    BLUE_JAZZ("BlueJazz", 'B', 50, 45, true, "Crop"),
    CARROT("Carrot", 'C', 35, 75, true, "Crop"),
    CAULIFLOWER("Cauliflower", 'C', 175, 75, true, "Crop"),
    COFFEE_BEAN("CoffeeBean", 'C', 15, 0, false, "Crop"),
    GARLIC("Garlic", 'G', 60, 20, true, "Crop"),
    GREEN_BEAN("GreenBean", 'G', 40, 25, false, "Crop"),
    KALE("Kale", 'K', 110, 50, true, "Crop"),
    PARSNIP("Parsnip", 'P', 35, 25, true, "Crop"),
    POTATO("Potato", 'P', 80, 25, true, "Crop"),
    RHUBARB("Rhubarb", 'R', 220, 0, true, "Crop"),
    STRAWBERRY("Strawberry", 'S', 120, 50, false, "Crop"),
    TULIP("Tulip", 'T', 30, 45, true, "Crop"),
    UNMILLED_RICE("UnmilledRice", 'U', 30, 3, true, "Crop"),
    BLUEBERRY("Blueberry", 'B', 50, 25, false, "Crop"),
    CORN("Corn", 'C', 50, 25, false, "Crop"),
    HOPS("Hops", 'H', 25, 45, false, "Crop"),
    HOT_PEPPER("HotPepper", 'H', 40, 13, false, "Crop"),
    MELON("Melon", 'M', 250, 113, true, "Crop"),
    POPPY("Poppy", 'P', 140, 45, true, "Crop"),
    RADISH("Radish", 'R', 90, 45, true, "Crop"),
    RED_CABBAGE("RedCabbage", 'R', 260, 75, true, "Crop"),
    STARFRUIT("Starfruit", 'S', 750, 125, true, "Crop"),
    SUMMER_SPANGLE("SummerSpangle", 'S', 90, 45, true, "Crop"),
    SUMMER_SQUASH("SummerSquash", 'S', 45, 63, false, "Crop"),
    SUNFLOWER("Sunflower", 'S', 80, 45, true, "Crop"),
    TOMATO("Tomato", 'T', 60, 20, false, "Crop"),
    WHEAT("Wheat", 'W', 25, 0, true, "Crop"),
    AMARANTH("Amaranth", 'A', 150, 50, true, "Crop"),
    ARTICHOKE("Artichoke", 'A', 160, 30, true, "Crop"),
    BEET("Beet", 'B', 100, 30, true, "Crop"),
    BOK_CHOY("Bok Choy", 'B', 80, 25, true, "Crop"),
    BROCCOLI("Broccoli", 'B', 70, 63, false, "Crop"),
    CRANBERRIES("Cranberries", 'C', 75, 38, false, "Crop"),
    EGGPLANT("Eggplant", 'E', 60, 20, false, "Crop"),
    FAIRY_ROSE("Fairy Rose", 'F', 290, 45, true, "Crop"),
    GRAPE("Grape", 'G', 80, 38, false, "Crop"),
    PUMPKIN("Pumpkin", 'P', 320, 0, true, "Crop"),
    YAM("Yam", 'Y', 160, 45, true, "Crop"),
    SWEET_GEM_BERRY("SweetGemBerry", 'S', 3000, 0, true, "Crop"),
    POWDERMELON("Powdermelon", 'P', 60, 63, true, "Crop"),
    ANCIENT_FRUIT("AncientFruit", 'A', 550, 0, false, "Crop"),
    SPEED_GRO("speedgro",'k',550,0,false,"SPEED_GRO");



    private final String name;
    private final char displayChar;
    private final int sellPrice;
    private final int energy;
    private final boolean isEdible;
    private final String type;

    SimpleItemInfo(String name, char displayChar, int sellPrice, int energy, boolean isEdible, String type) {
        this.name = name;
        this.displayChar = displayChar;
        this.sellPrice = sellPrice;
        this.energy = energy;
        this.isEdible = isEdible;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public char getDisplayChar() {
        return displayChar;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public String getType() {
        return type;
    }

    public static SimpleItemInfo fromName(String name) {
        for (SimpleItemInfo info : values()) {
            if (info.getName().equalsIgnoreCase(name)) {
                return info;
            }
        }
        return null;
    }

}
