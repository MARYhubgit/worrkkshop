package io.github.some_example_name.model.market;

import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Position;

import java.util.Map;

public interface MarketTemplate {
    TileType[][] generateLayout();
    int getWidth();
    int getHeight();
    String getName();
    Map<Position, String> getShopNames(); // <Position, ShopName>
    //Map<Position, String> getShopNames();
}
