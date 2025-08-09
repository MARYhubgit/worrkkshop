package io.github.some_example_name.model.shop;


import io.github.some_example_name.model.enums.Building;

public class AnimalProduct extends Product {
    private final Building requiredBuilding;

    public AnimalProduct(String name, int price, int stockLimitPerDay, Building requiredBuilding) {
        super(name, price, stockLimitPerDay);
        this.requiredBuilding = requiredBuilding;
    }

    public Building getRequiredBuilding() {
        return requiredBuilding;
    }
}
