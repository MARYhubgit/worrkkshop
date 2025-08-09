package io.github.some_example_name.model.region;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.farm.FarmTemplate;
import io.github.some_example_name.model.game.Position;
import io.github.some_example_name.model.game.Region;
import io.github.some_example_name.model.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.Map.entry;

public class FarmRegion extends Region {

    private final Tile[][] tiles;

    public FarmRegion(FarmTemplate template) {
        super(template.getWidth(), template.getHeight());
        this.tiles = new Tile[height][width];

        TileType[][] layout = template.generateLayout();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile tile = new Tile(new Position(row, col), this);
                tile.setType(layout[row][col]);
                tiles[row][col] = tile;
            }
        }
        //distributeRandomItems();
    }

    @Override
    public Tile getTileAt(int row, int col) {
        if (row >= 0 && row < height && col >= 0 && col < width) {
            return tiles[row][col];
        }
        return null;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
//    public void distributeRandomItems() {
//        Random rand = new Random();
//
//        // ساخت جدول آیتم‌ها با وزن
//        List<Item> itemPool = new ArrayList<>();
//        Map<String, ForagingData> foragingData = Map.ofEntries(
//                entry("Quartz", new ForagingData('Q', 20)),
//                entry("Earth Crystal", new ForagingData('E', 15)),
//                entry("Frozen Tear", new ForagingData('F', 10)),
//                entry("Fire Quartz", new ForagingData('R', 8)),
//                entry("Emerald", new ForagingData('M', 10)),
//                entry("Aquamarine", new ForagingData('A', 10)),
//                entry("Ruby", new ForagingData('B', 8)),
//                entry("Amethyst", new ForagingData('H', 8)),
//                entry("Topaz", new ForagingData('T', 8)),
//                entry("Jade", new ForagingData('J', 6)),
//                entry("Diamond", new ForagingData('D', 4)),
//                entry("Prismatic Shard", new ForagingData('P', 1)),
//                entry("Copper", new ForagingData('C', 12)),
//                entry("Iron", new ForagingData('I', 10)),
//                entry("Gold", new ForagingData('G', 6)),
//                entry("Iriduim", new ForagingData('U', 3)),
//                entry("Coal", new ForagingData('L', 10))
//        );
//
//        for (var entry : foragingData.entrySet()) {
//            String name = entry.getKey();
//            ForagingData data = entry.getValue();
//            for (int i = 0; i < data.weight; i++) {
//                itemPool.add(new ForagingItem(name, data.displayChar));
//            }
//        }
//
//        for (int i = 0; i < 30; i++) itemPool.add(new Tree());
//        for (int i = 0; i < 30; i++) itemPool.add(new Rock());
//
//
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                Tile tile = tiles[i][j];
//
//
//                if (tile.getType() == TileType.EMPTY && tile.getItem() == null) {
//                    if (rand.nextDouble() < 0.07) {
//                        Item randomItem = itemPool.get(rand.nextInt(itemPool.size()));
//                        tile.setItem(randomItem);
//                    }
//                }
//            }
//        }
//    }


    @Override
    public void printRegion() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                switch (tiles[row][col].getType()) {
                    case EMPTY -> System.out.print(". ");
                    case TREE -> System.out.print("T ");
                    case ROCK -> System.out.print("R ");
                    case LAKE -> System.out.print("L ");
                    case CABIN -> System.out.print("C ");
                    case GREENHOUSE -> System.out.print("G ");
                    case QUARRY -> System.out.print("Q ");
                    default -> System.out.print("? ");
                }
            }
            System.out.println();
        }
    }
}
