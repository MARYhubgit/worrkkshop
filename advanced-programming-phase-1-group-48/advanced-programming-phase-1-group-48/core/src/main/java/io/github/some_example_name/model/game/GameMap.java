package io.github.some_example_name.model.game;


import io.github.some_example_name.model.enums.TileType;

public class GameMap {
    private final int width;
    private final int height;
    private Tile[][] tiles;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[height][width];
        initializeEmptyMap();
    }

    private void initializeEmptyMap() {
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                tiles[y][x] = new Tile(TileType.EMPTY);
//            }
//        }
    }

    public boolean isInsideMap(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < width &&
                pos.getCol() >= 0 && pos.getCol() < height;
    }

    public Tile getTile(Position pos) {
        if (!isInsideMap(pos))
            return null;
        return tiles[pos.getCol()][pos.getRow()];
    }

    public void setTile(Position pos, TileType type) {
        if (!isInsideMap(pos))
            return;
        tiles[pos.getCol()][pos.getRow()].setType(type);
    }

    public void printSection(Position topLeft, int size) {

    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
