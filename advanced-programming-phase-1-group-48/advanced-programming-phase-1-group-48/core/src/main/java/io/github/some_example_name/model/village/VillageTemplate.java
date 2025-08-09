package io.github.some_example_name.model.village;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Position;

import java.util.Map;

public interface VillageTemplate {
    TileType[][] generateLayout();
    int getWidth();
    int getHeight();
    String getName();
    Map<Position, String> getNpcHomes(); // <Position, NPCName>
}

