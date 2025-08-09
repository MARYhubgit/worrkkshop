package io.github.some_example_name.model.enums;

public enum BackpackType {
    SMALL(12, false),
    MEDIUM(24, false),
    DELUXE(Integer.MAX_VALUE, true);

    private final int capacity;
    private final boolean unlimited;

    BackpackType(int capacity, boolean unlimited) {
        this.capacity = capacity;
        this.unlimited = unlimited;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isUnlimited() {
        return unlimited;
    }
}
