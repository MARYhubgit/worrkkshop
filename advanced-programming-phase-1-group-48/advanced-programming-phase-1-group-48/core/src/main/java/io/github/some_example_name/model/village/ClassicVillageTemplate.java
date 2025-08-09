package io.github.some_example_name.model.village;

import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Position;

import java.util.HashMap;
import java.util.Map;

public class ClassicVillageTemplate implements VillageTemplate {

    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    private final Map<Position, String> npcHomes = new HashMap<>();

    @Override
    public TileType[][] generateLayout() {
        TileType[][] layout = new TileType[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                layout[i][j] = TileType.EMPTY;

        addNpcHouse(layout, 6, 2, "Abigail");
        addNpcHouse(layout, 6, 12, "Harvey");
        addNpcHouse(layout, 6, 22, "Lia");
        addNpcHouse(layout, 19, 6, "Robin");
        addNpcHouse(layout, 19, 18, "Sebastian");

        return layout;
    }

    private void addNpcHouse(TileType[][] layout, int x, int y, String name) {
        for (int i = x - 1; i <= x + 6; i++) {
            for (int j = y - 1; j <= y + 6; j++) {
                if (i == x - 1 || i == x + 6 || j == y - 1 || j == y + 6)
                    layout[i][j] = TileType.FARM_BORDER;
            }
        }

        layout[x + 6][y + 3] = TileType.EMPTY;

        for (int i = x; i < x + 6; i++)
            for (int j = y; j < y + 6; j++)
                layout[i][j] = TileType.NPC_HOUSE;


        npcHomes.put(new Position(x + 2, y + 2), name);
    }

    @Override public int getWidth() { return WIDTH; }
    @Override public int getHeight() { return HEIGHT; }
    @Override public String getName() { return "Classic Village"; }
    @Override public Map<Position, String> getNpcHomes() { return npcHomes; }
}
