package io.github.some_example_name.model.Plant.Enums;


import io.github.some_example_name.model.Plant.SeedProvider;
import io.github.some_example_name.model.enums.Season;

import java.util.*;
import java.util.stream.Collectors;

public enum MixSeedType implements SeedProvider<CropType> {
    SPRING_MIX("Spring", Season.SPRING, Arrays.asList(
            CropType.CAULIFLOWER,
            CropType.PARSNIP,
            CropType.POTATO,
            CropType.BLUE_JAZZ,
            CropType.TULIP
    )),
    SUMMER_MIX("Summer", Season.SUMMER, Arrays.asList(
            CropType.CORN,
            CropType.HOT_PEPPER,
            CropType.RADISH,
            CropType.WHEAT,
            CropType.POPPY,
            CropType.SUNFLOWER,
            CropType.SUMMER_SPANGLE
    )),
    FALL_MIX("Fall", Season.FALL, Arrays.asList(
            CropType.ARTICHOKE,
            CropType.CORN,
            CropType.EGGPLANT,
            CropType.PUMPKIN,
            CropType.SUNFLOWER,
            CropType.FAIRY_ROSE
    )),
    WINTER_MIX("Winter", Season.WINTER, Collections.singletonList(
            CropType.POWDERMELON
    ));

    private final String name;
    private final Season season;
    private final List<CropType> possibleCrops;

    private static final Map<String, MixSeedType> BY_NAME = new HashMap<>();
    private static final Random RNG = new Random();

    static {
        for (MixSeedType ms : values()) {
            BY_NAME.put(ms.name.toLowerCase(), ms);
        }
    }

    MixSeedType(String name, Season season, List<CropType> possibleCrops) {
        this.name = name;
        this.season = season;
        this.possibleCrops = Collections.unmodifiableList(possibleCrops);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Season getSeason() {
        return season;
    }

    @Override
    public List<CropType> getPossiblePlants() {
        return possibleCrops;
    }

    /**
     * Lookup by name (case-insensitive)
     */
    public static Optional<MixSeedType> fromName(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(BY_NAME.get(name.toLowerCase()));
    }

    /**
     * انتخاب تصادفی یکی از محصولات مجاز این MixSeed
     */
    public CropType getRandomPlant() {
        int idx = RNG.nextInt(possibleCrops.size());
        return possibleCrops.get(idx);
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s%nSeason: %s%nPossible Crops: %s",
                name,
                season,
                possibleCrops.stream()
                        .map(CropType::getName)
                        .collect(Collectors.joining(", "))
        );
    }
}
