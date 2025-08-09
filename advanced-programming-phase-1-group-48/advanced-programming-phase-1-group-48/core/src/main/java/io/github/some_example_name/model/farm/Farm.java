package io.github.some_example_name.model.farm;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.GameMap;
import io.github.some_example_name.model.game.Position;

public class Farm {
    private String farmId;
    private Position topLeft;
    private int width;
    private int height;
    private GameMap farmMap;

    public Farm(String farmId, Position topLeft, int width, int height) {
        this.farmId = farmId;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.farmMap = new GameMap(width, height);
        initializeStaticElements();
    }


    private void initializeStaticElements() {
        Position cabinPos = new Position(0, 0);
        for (int y = cabinPos.getCol(); y < cabinPos.getCol() + 4 && y < height; y++) {
            for (int x = cabinPos.getRow(); x < cabinPos.getRow() + 4 && x < width; x++) {
                farmMap.setTile(new Position(x, y), TileType.CABIN);
            }
        }
    }


    public void distributeRandomItems() {
    }

    public GameMap getFarmMap() {
        return farmMap;
    }

    public String getFarmId() {
        return farmId;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
