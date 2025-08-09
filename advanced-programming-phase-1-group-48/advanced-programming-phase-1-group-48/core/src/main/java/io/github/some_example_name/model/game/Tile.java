package io.github.some_example_name.model.game;


import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.items.Item;

import java.util.Map;

public class Tile {

    private  Position position;    // موقعیت تایل در نقشه منطقه
    private TileType type;              // نوع فعلی تایل
    private TileType defaultType;       // نوع اولیه تایل (قبل از اینکه مثلاً PLAYER بشه)
    private Item item;                  // آیتم روی تایل
    private boolean isWalkable;         // قابل عبور بودن یا نه
    ///private  Region region;// ریجن صاحب تایل
    private TileType originalType;

    private transient Region region;

    public Tile() {}

    public Tile(Position position, Region region) {
        this.position = position;
        this.region = region;
        this.type = TileType.EMPTY;
        this.defaultType = TileType.EMPTY;
        this.item = null;
        this.isWalkable = true;
    }

    public Position getPosition() {
        return position;
    }

//    public void setPosition(Position position) {
//        this.position = position;
//    }

    public int getRow() {
        return position.getRow();
    }

    public int getCol() {
        return position.getCol();
    }


    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        if (this.originalType == null) {
            this.originalType = type;
        }
        this.type = type;
        updateWalkableStatus();
    }

    public void restoreOriginalType() {
        this.type = originalType != null ? originalType : TileType.EMPTY;
        updateWalkableStatus();
    }


    public void resetToDefaultType() {
        this.type = this.defaultType;
        updateWalkableStatus();
    }

    public TileType getDefaultType() {
        return defaultType;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public Region getRegion() {
        return region;
    }

    private void updateWalkableStatus() {
        switch (this.type) {
            case ROCK, TREE, LAKE, PLAYER, GREENHOUSE, FARM_BORDER -> this.isWalkable = false;
            default -> this.isWalkable = true;
        }
    }

    @Override
    public String toString() {
        return "Tile{pos=" + position + ", type=" + type + ", item=" + item + "}";
    }
    public static final Map<String, TileType> artisanTileMap = Map.ofEntries(
        Map.entry("bee house", TileType.ARTISAN_BEE_HOUSE),
        Map.entry("cheese press", TileType.ARTISAN_CHEESE_PRESS),
        Map.entry("keg", TileType.ARTISAN_KEG),
        Map.entry("mayonnaise machine", TileType.ARTISAN_MAYO_MACHINE),
        Map.entry("charcoal klin", TileType.ARTISAN_CHARCOAL_KILN),
        Map.entry("preserves jar", TileType.ARTISAN_PRESERVES_JAR),
        Map.entry("dehydrator", TileType.ARTISAN_DEHYDRATOR),
        Map.entry("oil maker", TileType.ARTISAN_OIL_MAKER),
        Map.entry("loom", TileType.ARTISAN_LOOM),
        Map.entry("fish smoker", TileType.ARTISAN_FISH_SMOKER),
        Map.entry("furnace", TileType.ARTISAN_FURNACE)
    );
}
