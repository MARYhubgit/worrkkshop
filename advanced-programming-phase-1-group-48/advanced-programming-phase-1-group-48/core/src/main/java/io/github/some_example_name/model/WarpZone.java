package io.github.some_example_name.model;

public class WarpZone {
    public String targetMap;
    public int targetX;
    public int targetY;
    public float x, y, width, height;

    public WarpZone(float x, float y, float width, float height, String targetMap, int targetX, int targetY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.targetMap = targetMap;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public boolean contains(float playerX, float playerY) {
        return playerX >= x && playerX <= x + width &&
            playerY >= y && playerY <= y + height;
    }
}
