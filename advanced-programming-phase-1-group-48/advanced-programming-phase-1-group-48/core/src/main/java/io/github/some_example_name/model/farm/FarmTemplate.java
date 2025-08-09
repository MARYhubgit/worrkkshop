package io.github.some_example_name.model.farm;


import io.github.some_example_name.model.enums.TileType;

public interface FarmTemplate {
    TileType[][] generateLayout();
    int getWidth();
    int getHeight();
    String getName();
}
