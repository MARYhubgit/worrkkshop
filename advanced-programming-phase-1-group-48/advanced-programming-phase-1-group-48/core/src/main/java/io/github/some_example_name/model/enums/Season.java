package io.github.some_example_name.model.enums;

public enum Season {
    SPRING,
    SUMMER,
    FALL,
    WINTER,
    SPECIAL;

    public static Season  fromString(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Season string is null");
        }
        s = s.trim().toLowerCase();
        switch (s) {
            case "spring":           return SPRING;
            case "summer":           return SUMMER;
            case "fall": case "autumn": return FALL;
            case "winter":           return WINTER;
            default:
                return SPECIAL;
        }
    }
}
