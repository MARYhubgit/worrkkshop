package io.github.some_example_name.model.Plant.Enums;

import io.github.some_example_name.model.Plant.PlantType;
import io.github.some_example_name.model.enums.Season;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum SeedType implements PlantType {
    JAZZ_SEEDS("JazzSeeds", Season.SPRING),
    CARROT_SEEDS("CarrotSeeds", Season.SPRING),
    CAULIFLOWER_SEEDS("CauliflowerSeeds", Season.SPRING),
    COFFEE_BEAN("CoffeeBean", Season.SPRING),
    GARLIC_SEEDS("GarlicSeeds", Season.SPRING),
    BEAN_STARTER("BeanStarter", Season.SPRING),
    KALE_SEEDS("KaleSeeds", Season.SPRING),
    PARSNIP_SEEDS("ParsnipSeeds", Season.SPRING),
    POTATO_SEEDS("PotatoSeeds", Season.SPRING),
    RHUBARB_SEEDS("RhubarbSeeds", Season.SPRING),
    STRAWBERRY_SEEDS("StrawberrySeeds", Season.SPRING),
    TULIP_BULB("TulipBulb", Season.SPRING),
    RICE_SHOOT("RiceShoot", Season.SPRING),

    BLUEBERRY_SEEDS("BlueberrySeeds", Season.SUMMER),
    CORN_SEEDS("CornSeeds", Season.SUMMER),
    HOPS_STARTER("HopsStarter", Season.SUMMER),
    PEPPER_SEEDS("PepperSeeds", Season.SUMMER),
    MELON_SEEDS("MelonSeeds", Season.SUMMER),
    POPPY_SEEDS("PoppySeeds", Season.SUMMER),
    RADISH_SEEDS("RadishSeeds", Season.SUMMER),
    RED_CABBAGE_SEEDS("RedCabbageSeeds", Season.SUMMER),
    STARFRUIT_SEEDS("Starfruit Seeds", Season.SUMMER),
    SPANGLE_SEEDS("SpangleSeeds", Season.SUMMER),
    SUMMER_SQUASH_SEEDS("SummerSquashSeeds", Season.SUMMER),
    SUNFLOWER_SEEDS("SunflowerSeeds", Season.SUMMER),
    TOMATO_SEEDS("TomatoSeeds", Season.SUMMER),
    WHEAT_SEEDS("WheatSeeds", Season.SUMMER),

    AMARANTH_SEEDS("AmaranthSeeds", Season.FALL),
    ARTICHOKE_SEEDS("ArtichokeSeeds", Season.FALL),
    BEET_SEEDS("BeetSeeds", Season.FALL),
    BOK_CHOY_SEEDS("BokChoySeeds", Season.FALL),
    BROCCOLI_SEEDS("BroccoliSeeds", Season.FALL),
    CRANBERRY_SEEDS("CranberrySeeds", Season.FALL),
    EGGPLANT_SEEDS("EggplantSeeds", Season.FALL),
    FAIRY_SEEDS("FairySeeds", Season.FALL),
    GRAPE_STARTER("GrapeStarter", Season.FALL),
    PUMPKIN_SEEDS("PumpkinSeeds", Season.FALL),
    YAM_SEEDS("YamSeeds", Season.FALL),
    RARE_SEED("RareSeed", Season.FALL),

    POWDERMELON_SEEDS("PowdermelonSeeds", Season.WINTER),

    ANCIENT_SEEDS("AncientSeeds", Season.SPECIAL),
    MIXED_SEEDS("MixedSeeds", Season.SPECIAL),

    ;
    private final String name;
    private final Season season;
    SeedType(String name, Season season) {
        this.name = name;
        this.season = season;
    }
    @Override public String getName() { return name; }
    private static final Map<String, SeedType> BY_NAME = new HashMap<>();
    static { for (SeedType s : values()) BY_NAME.put(s.name.toLowerCase(), s); }
    public static Optional<SeedType> fromName(String name) {
        return name==null?Optional.empty():Optional.ofNullable(BY_NAME.get(name.toLowerCase()));
    }

    @Override
    public String toString() {
        return String.format("Name: %s%n" +
                "Season: %s", name, season);
    }
}

