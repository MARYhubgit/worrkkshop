package io.github.some_example_name.model.building;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.GameMap;
import io.github.some_example_name.model.game.Position;

public abstract class Buildings {
    protected String name;
    protected Position topLeft;
    protected int width;
    protected int height;

    public Buildings(String name, Position topLeft, int width, int height) {
        this.name = name;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
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

    public void placeOnMap(GameMap map, TileType type) {
    }

    public abstract void interact();
}

