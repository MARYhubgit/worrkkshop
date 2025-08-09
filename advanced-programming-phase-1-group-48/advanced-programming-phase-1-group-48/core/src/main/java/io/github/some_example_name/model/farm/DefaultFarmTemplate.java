package io.github.some_example_name.model.farm;


import io.github.some_example_name.model.enums.TileType;

public class DefaultFarmTemplate implements FarmTemplate {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    @Override
    public TileType[][] generateLayout() {
        TileType[][] layout = new TileType[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                layout[i][j] = TileType.EMPTY;


        for (int i = 0; i < HEIGHT; i++) {
            layout[i][0] = TileType.FARM_BORDER;
            layout[i][WIDTH - 1] = TileType.FARM_BORDER;
        }
        for (int j = 0; j < WIDTH; j++) {
            layout[0][j] = TileType.FARM_BORDER;
            layout[HEIGHT - 1][j] = TileType.FARM_BORDER;
        }


        layout[HEIGHT - 1][WIDTH / 2] = TileType.EMPTY;


        for (int i = 2; i < 6; i++)
            for (int j = 2; j < 6; j++)
                layout[i][j] = TileType.CABIN;


        for (int i = HEIGHT - 8; i < HEIGHT - 3; i++)
            for (int j = WIDTH - 8; j < WIDTH - 2; j++)
                layout[i][j] = TileType.GREENHOUSE;


        for (int i = 12; i < 17; i++)
            for (int j = 10; j < 14; j++)
                layout[i][j] = TileType.LAKE;


        for (int i = 5; i < 10; i++)
            for (int j = 20; j < 26; j++)
                layout[i][j] = TileType.QUARRY;

        return layout;
    }

    @Override public int getWidth() { return WIDTH; }
    @Override public int getHeight() { return HEIGHT; }
    @Override public String getName() { return "Default Farm"; }
}
