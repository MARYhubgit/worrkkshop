package io.github.some_example_name.model.Player.inventory;


import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.TrashCanType;

public class TrashCan {
    private TrashCanType type;

    public TrashCan(TrashCanType type) {
        this.type = type;
    }
    public Result upgrade(TrashCanType newType) {
        if (newType.ordinal() > type.ordinal()) {
            this.type = newType;
            return Result.success("Trash can upgraded successfully.");
        } else {
            return Result.failure("You already have an equal or better trash can.");
        }
    }

    public TrashCanType getType() {
        return type;
    }
    public int getRefundAmount(int itemSellPrice) {
        double refundRate = switch (type) {
            case BASIC -> 0.0;
            case COPPER -> 0.15;
            case IRON -> 0.30;
            case GOLD -> 0.45;
            case IRIDIUM -> 0.60;
        };
        return (int) Math.round(itemSellPrice * refundRate);
    }

}
