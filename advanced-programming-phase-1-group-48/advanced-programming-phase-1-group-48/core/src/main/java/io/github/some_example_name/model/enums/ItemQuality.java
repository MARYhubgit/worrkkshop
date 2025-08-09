package io.github.some_example_name.model.enums;

public enum ItemQuality {
    REGULAR,
    SILVER,
    GOLD,
    IRIDIUM;

    public static ItemQuality fromItemName(String name) {
        String lower = name.toLowerCase();
        if (lower.contains("iridium")) return IRIDIUM;
        if (lower.contains("gold")) return GOLD;
        if (lower.contains("silver")) return SILVER;
        return REGULAR;
    }
}
