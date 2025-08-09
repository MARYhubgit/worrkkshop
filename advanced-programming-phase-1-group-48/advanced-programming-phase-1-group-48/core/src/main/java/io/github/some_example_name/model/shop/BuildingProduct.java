package io.github.some_example_name.model.shop;



import io.github.some_example_name.model.enums.Building;

import java.util.Map;

public class BuildingProduct extends Product {
    private final Building buildingType;
    private final String size;

    public BuildingProduct(String name, int basePrice, int stockLimitPerDay,
                           Building buildingType, Map<String, Integer> requiredIngredients, String size) {
        super(name, basePrice, stockLimitPerDay, requiredIngredients);
        this.buildingType = buildingType;
        this.size = size;
    }

    public Building getBuildingType() {
        return buildingType;
    }

    public String getSize() {
        return size;
    }
}
