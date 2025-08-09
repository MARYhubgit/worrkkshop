// ✅ VillageRegion.java
package io.github.some_example_name.model.region;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.game.Position;
import io.github.some_example_name.model.game.Region;
import io.github.some_example_name.model.game.Tile;
import io.github.some_example_name.model.village.VillageTemplate;

import java.util.HashMap;
import java.util.Map;

public class VillageRegion extends Region {
    private final Tile[][] tiles;
    private final Map<Position, String> npcNames = new HashMap<>();

    public VillageRegion(VillageTemplate template) {
        super(template.getWidth(), template.getHeight());
        this.tiles = new Tile[height][width];

        TileType[][] layout = template.generateLayout();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Position pos = new Position(i, j);
                Tile tile = new Tile(pos, this);
                tile.setType(layout[i][j]);
                tiles[i][j] = tile;
            }
        }

        this.npcNames.putAll(template.getNpcHomes());
    }

    public Tile getTileAt(int x, int y) {
        return isInsideBounds(x, y) ? tiles[x][y] : null;
    }

    public boolean isInsideBounds(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    public Map<Position, String> getNpcNames() {
        return npcNames;
    }

    @Override
    public void printRegion() {

    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
