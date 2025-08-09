package io.github.some_example_name.model.building;


import io.github.some_example_name.model.game.GameMap;
import io.github.some_example_name.model.game.Position;

public class House extends Buildings {
    private String houseId;
    private Position topLeft;
    private int width;
    private int height;
    private GameMap houseMap;

    public House(String houseId, Position topLeft, int width, int height) {
        super(houseId, topLeft, width, height);
        this.houseId = houseId;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.houseMap = new GameMap(width, height);
        initializeHouse();
    }

    private void initializeHouse() {
        // houseMap.setTile(new Position(0, 0), TileType.CABIN);
    }

    public void printHouse() {
        houseMap.printSection(new Position(0, 0), Math.min(width, height));
    }

    public String getHouseId() { return houseId; }
    public Position getTopLeft() { return topLeft; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    @Override
    public void interact() {

    }
}
