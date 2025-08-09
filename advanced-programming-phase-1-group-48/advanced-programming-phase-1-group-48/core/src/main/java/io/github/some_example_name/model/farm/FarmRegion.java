package io.github.some_example_name.model.farm;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Position;
import io.github.some_example_name.model.game.Region;
import io.github.some_example_name.model.game.Tile;

public class FarmRegion extends Region {

    private final int farmWidth;
    private final int farmHeight;
    private final Tile[][] tiles;
    private String ownerName;

    public FarmRegion(FarmTemplate template) {
        super(template.getWidth(), template.getHeight());
        this.farmWidth = template.getWidth();
        this.farmHeight = template.getHeight();
        this.tiles = new Tile[farmHeight][farmWidth];

        TileType[][] layout = template.generateLayout();

        for (int i = 0; i < farmHeight; i++) {
            for (int j = 0; j < farmWidth; j++) {
                Position pos = new Position(i, j);
                Tile tile = new Tile(pos, this);
                tile.setType(layout[i][j]);
                tiles[i][j] = tile;
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (isInsideBounds(x, y)) {
            return tiles[x][y];
        }
        return null;
    }

    public boolean isInsideBounds(int x, int y) {
        return (x >= 0 && x < farmHeight && y >= 0 && y < farmWidth);
    }

    public int getFarmWidth() {
        return farmWidth;
    }

    public int getFarmHeight() {
        return farmHeight;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String name) { this.ownerName = name; }
    // برای تست: چاپ کل نقشه
    public void printRegion() {
        for (int i = 0; i < farmHeight; i++) {
            for (int j = 0; j < farmWidth; j++) {
                switch (tiles[i][j].getType()) {
                    case EMPTY:
                        System.out.print(". ");
                        break;
                    case TREE:
                        System.out.print("T ");
                        break;
                    case ROCK:
                        System.out.print("R ");
                        break;
                    //case WATER:
                    case LAKE:
                        System.out.print("L ");
                        break;
                    case CABIN:
                        System.out.print("C ");
                        break;
                    case GREENHOUSE:
                        System.out.print("G ");
                        break;
                    case QUARRY:
                        System.out.print("Q ");
                        break;
                    case NPC_HOUSE:
                        System.out.print("N ");
                        break;
                    case SHOP:
                        System.out.print("S ");
                        break;
                    default:
                        System.out.print("? ");
                        break;
                }
            }
            System.out.println();
        }
    }
}
