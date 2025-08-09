package io.github.some_example_name.model.market;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Position;

import java.util.HashMap;
import java.util.Map;

public class ClassicMarketTemplate implements MarketTemplate {

    private static final int WIDTH = 30;
    private static final int HEIGHT = 34; // ⬅️ بزرگ‌تر شد برای اضافه شدن Saloon

    private final Map<Position, String> shops = new HashMap<>();

    @Override
    public TileType[][] generateLayout() {
        TileType[][] layout = new TileType[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                layout[i][j] = TileType.EMPTY;

        addShop(layout, 2, 2, "Blacksmith");
        addShop(layout, 2, 12, "Carpenter");
        addShop(layout, 2, 22, "GeneralStore");
        addShop(layout, 12, 2, "FishShop");
        addShop(layout, 12, 12, "JojaMart");
        addShop(layout, 12, 22, "Ranch");
        addShop(layout, 24, 12, "Saloon");

        return layout;
    }

    private void addShop(TileType[][] layout, int x, int y, String name) {
        for (int i = x - 1; i <= x + 6; i++) {
            for (int j = y - 1; j <= y + 6; j++) {
                if (i == x - 1 || i == x + 6 || j == y - 1 || j == y + 6)
                    layout[i][j] = TileType.FARM_BORDER;
            }
        }

        layout[x + 6][y + 3] = TileType.EMPTY;

        for (int i = x; i < x + 6; i++) {
            for (int j = y; j < y + 6; j++) {
                layout[i][j] = TileType.SHOP;
                shops.put(new Position(i, j), name);
            }
        }
    }

    @Override public int getWidth() { return WIDTH; }

    @Override public int getHeight() { return HEIGHT; }

    @Override public String getName() { return "Classic Market"; }

    @Override public Map<Position, String> getShopNames() { return shops; }
}
