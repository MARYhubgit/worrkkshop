package io.github.some_example_name.model.Plant.Enums;

import io.github.some_example_name.model.Plant.PlantType;
import io.github.some_example_name.model.enums.Season;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ForTreeType implements PlantType {
    ACORNS("Acorns", Season.SPECIAL),
    MAPLE_SEEDS("Maple Seeds", Season.SPECIAL),
    PINE_CONES("Pine Cones", Season.SPECIAL),
    MAHOGANY_SEEDS("Mahogany Seeds", Season.SPECIAL),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seeds", Season.SPECIAL);

    private final String name;
    private final Season season;

    ForTreeType(String name, Season season) {
        this.name = name;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    // ---------- lookup map & initializer ----------
    private static final Map<String, ForTreeType> BY_NAME = new HashMap<>();
    static {
        for (ForTreeType t : values()) {
            BY_NAME.put(t.name.toLowerCase(), t);
        }
    }

    /**
     * جست‌وجو بر اساس نام (حساس به حروف بزرگ/کوچک نیست)
     */
    public static Optional<ForTreeType> fromName(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(BY_NAME.get(name.toLowerCase()));
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s%nSeason: %s",
                name, season
        );
    }
}
