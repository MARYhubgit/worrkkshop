package io.github.some_example_name.model.game;

public abstract class Region {

    protected final int width;
    protected final int height;

    protected int offsetX;
    protected int offsetY;

    public Region(int width, int height) {
        this.width = width;
        this.height = height;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public int getFarmWidth() {
        return width;
    }

    public int getFarmHeight() {
        return height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public abstract Tile getTileAt(int row, int col);

    public abstract void printRegion();

    public Position getGlobalPosition(Position local) {
        return new Position(offsetY + local.getRow(), offsetX + local.getCol());
    }
}
