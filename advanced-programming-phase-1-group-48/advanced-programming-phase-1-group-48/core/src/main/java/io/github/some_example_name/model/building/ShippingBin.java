package io.github.some_example_name.model.building;


import io.github.some_example_name.model.game.Position;

public class ShippingBin extends Buildings {
    public ShippingBin(Position topLeft) {
        super("Shipping Bin", topLeft, 2, 2);
    }

    @Override
    public void interact() {

    }

}

