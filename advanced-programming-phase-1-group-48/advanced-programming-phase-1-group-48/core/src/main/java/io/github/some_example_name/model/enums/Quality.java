package io.github.some_example_name.model.enums;


public enum Quality {
    NORMAL(1.0),
    SILVER(1.25),
    GOLD(1.5),
    IRIDIUM(2.0);

    private final double multiplier;

    Quality(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public static Quality fromString(String value) {
        return switch (value.toLowerCase()) {
            case "silver" -> SILVER;
            case "gold" -> GOLD;
            case "iridium" -> IRIDIUM;
            default -> NORMAL;
        };
    }
}


