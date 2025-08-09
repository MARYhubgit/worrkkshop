package io.github.some_example_name.model.Player.inventory;

import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.BackpackType;

public class Backpack {
    private BackpackType type;

    public Backpack(BackpackType type) {
        this.type = type;
    }

    public int getCapacity() {
        return type.getCapacity();
    }

    public boolean isUnlimited() {
        return type.isUnlimited();
    }

    public BackpackType getType() {
        return type;
    }

    public Result upgrade(BackpackType newType) {
        if (newType.getCapacity() > type.getCapacity()) {
            this.type = newType;
            return Result.success("Backpack upgraded successfully!");
        } else {
            return Result.failure("You already have an equal or better backpack.");
        }
    }

}
