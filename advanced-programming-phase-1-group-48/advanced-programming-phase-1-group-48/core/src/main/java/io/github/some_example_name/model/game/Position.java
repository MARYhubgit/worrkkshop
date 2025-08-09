package io.github.some_example_name.model.game;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
public class Position {
    private int row;
    private int col;


    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public Position getAdjacent(String direction) {
        return switch (direction.toLowerCase()) {
            case "up" -> new Position(row - 1, col);
            case "down" -> new Position(row + 1, col);
            case "left" -> new Position(row, col - 1);
            case "right" -> new Position(row, col + 1);
            case "upleft" -> new Position(row - 1, col - 1);
            case "upright" -> new Position(row - 1, col + 1);
            case "downleft" -> new Position(row + 1, col - 1);
            case "downright" -> new Position(row + 1, col + 1);
            default -> null;
        };
    }
    public Position() {}

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Position translate(int dRow, int dCol) {
        return new Position(row + dRow, col + dCol);
    }
    public List<Position> getAdjacentPositions() {

        List<Position> adjacent = new ArrayList<>();

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < 8; i++) {
            adjacent.add(new Position(this.row + dx[i], this.col + dy[i]));
        }



        return adjacent;

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
