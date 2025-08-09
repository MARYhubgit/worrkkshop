package io.github.some_example_name.model.enums;

public enum TrashCanType {
    BASIC(0),
    COPPER(0.15),
    IRON(0.30),
    GOLD(0.45),
    IRIDIUM(0.60);

    private final double refundRate;

    TrashCanType(double refundRate) {
        this.refundRate = refundRate;
    }

    public double getRefundRate() {
        return refundRate;
    }
}
