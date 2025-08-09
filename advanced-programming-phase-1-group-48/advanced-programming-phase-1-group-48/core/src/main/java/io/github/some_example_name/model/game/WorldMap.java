package io.github.some_example_name.model.game;



import io.github.some_example_name.model.AnsiColor;
import io.github.some_example_name.model.NPC.NPC;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.enums.TileType;
import io.github.some_example_name.model.farm.FarmRegion;
import io.github.some_example_name.model.farm.FarmTemplate;
import io.github.some_example_name.model.market.MarketTemplate;
import io.github.some_example_name.model.region.MarketRegion;
import io.github.some_example_name.model.region.VillageRegion;
import io.github.some_example_name.model.village.VillageTemplate;

import java.util.*;

public class WorldMap {

    private final int WORLD_WIDTH = 200;
    private final int WORLD_HEIGHT = 200;

    private final List<Region> regions = new ArrayList<>();
    private final Map<Player, FarmRegion> playerFarms = new HashMap<>();
    private final Tile[][] worldGrid = new Tile[WORLD_HEIGHT][WORLD_WIDTH];
    private final List<NPC> npcs = new ArrayList<>();

    public WorldMap() {
    }

    public WorldMap(Map<Player, FarmTemplate> playerChoices,
                    VillageTemplate villageTemplate,
                    MarketTemplate marketTemplate) {
        initializeWorldGrid();
        setupFarms(playerChoices);
        generateRandomTreesInFarms();
        generateRandomForagingItems();
        updateQuarryStonesDaily();
        setupVillage(villageTemplate);
        setupMarket(marketTemplate);
        initializeNPCs();
    }

    public void initializeNPCs() {
        List<NPC> npcs = new ArrayList<>();

        NPC abigail = new NPC("Abigail", "Artist", "Abigail");
        abigail.addFavoriteItem("pumpkin");
        abigail.addFavoriteItem("iron ore");
        abigail.addFavoriteItem("coffee");


        NPC harvey = new NPC("Harvey", "Doctor", "Harvey");
        harvey.addFavoriteItem("wine");
        harvey.addFavoriteItem("salmon");
        harvey.addFavoriteItem("coffee");


        NPC lia = new NPC("Lia", "Sculptor", "Lia");
        lia.addFavoriteItem("salad");
        lia.addFavoriteItem("grape");
        lia.addFavoriteItem("wine");


        NPC robin = new NPC("Robin", "Carpenter", "Robin");
        robin.addFavoriteItem("spaghetti");
        robin.addFavoriteItem("wood");
        robin.addFavoriteItem("iron bar");


        NPC sebastian = new NPC("Sebastian", "Programmer", "Sebastian");
        sebastian.addFavoriteItem("fiber");
        sebastian.addFavoriteItem("pumpkin pie");
        sebastian.addFavoriteItem("pizza");

        abigail.addGiftOption("pumpkin");
        abigail.addGiftOption("coffee");

        harvey.addGiftOption("wine");

        lia.addGiftOption("salad");

        robin.addGiftOption("wood");

        sebastian.addGiftOption("pizza");

        npcs.addAll(List.of(abigail, harvey, lia, robin, sebastian));

        this.npcs.addAll(npcs);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public void initializeWorldGrid() {
        for (int y = 0; y < WORLD_HEIGHT; y++) {
            for (int x = 0; x < WORLD_WIDTH; x++) {
                Position pos = new Position(x, y);
                worldGrid[y][x] = new Tile(pos, null);
                worldGrid[y][x].setType(TileType.EMPTY);
            }
        }
    }

    public void printMapSegment(int x, int y, int size) {
        for (int i = y; i < y + size; i++) {
            for (Map.Entry<Player, FarmRegion> entry : playerFarms.entrySet()) {
                FarmRegion farm = entry.getValue();
                int topY = farm.getOffsetY();
                if (topY == i) {
                    int startX = farm.getOffsetX();
                    if (startX >= x && startX < x + size) {
                        int pos = Math.max(0, startX - x);
                        for (int k = 0; k < pos; k++) System.out.print("  ");
                        System.out.println("🌾 " + entry.getKey().getName() + "'s Farm");
                    }
                }
            }

            for (int j = x; j < x + size; j++) {
                Tile tile = getTileAt(j, i);
                if (tile == null) {
                    System.out.print("  ");
                    continue;
                }

                TileType type = tile.getType();
                Region region = tile.getRegion();
                Position pos = tile.getPosition();

                switch (type) {
                    case EMPTY -> {
                        if (region instanceof FarmRegion)
                            System.out.print(AnsiColor.YELLOW + ". " + AnsiColor.RESET);
                        else if (region instanceof VillageRegion)
                            System.out.print(AnsiColor.ORANGE + ". " + AnsiColor.RESET);
                        else if (region instanceof MarketRegion)
                            System.out.print(AnsiColor.PINK + ". " + AnsiColor.RESET);
                        else System.out.print("  ");
                    }
                    case TREE -> System.out.print(AnsiColor.GREEN + "T " + AnsiColor.RESET);
                    case ROCK -> System.out.print(AnsiColor.GRAY + "R " + AnsiColor.RESET);
                    case FORAGING -> System.out.print(AnsiColor.LIGHT_GREEN + "F " + AnsiColor.RESET);
                    case LAKE -> System.out.print(AnsiColor.BLUE + "L " + AnsiColor.RESET);
                    case CABIN -> System.out.print(AnsiColor.BROWN + "C " + AnsiColor.RESET);
                    case GREENHOUSE -> System.out.print(AnsiColor.CYAN + "G " + AnsiColor.RESET);
                    case QUARRY -> System.out.print(AnsiColor.DARK_GRAY + "Q " + AnsiColor.RESET);
                    case FARM_BORDER -> System.out.print(AnsiColor.PURPLE + "# " + AnsiColor.RESET);
                    case PLAYER -> System.out.print(AnsiColor.BLUE + "@ " + AnsiColor.RESET);
                    case TILLED_SOIL -> System.out.print(AnsiColor.LIGHT_GREEN + "t" + AnsiColor.RESET);
                    case CROP -> System.out.print(AnsiColor.GREEN + " c " + AnsiColor.RESET);
                    case CHARCOAL -> System.out.print(AnsiColor.DARK_GRAY + " ! " + AnsiColor.RESET);
                    case ARTISAN_BEE_HOUSE -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_CHEESE_PRESS -> System.out.print(AnsiColor.BLUE + "A" + AnsiColor.RESET);
                    case ARTISAN_KEG -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_MAYO_MACHINE -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_CHARCOAL_KILN -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_PRESERVES_JAR -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_DEHYDRATOR -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_OIL_MAKER -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_LOOM -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_FISH_SMOKER -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case ARTISAN_FURNACE -> System.out.print(AnsiColor.BLUE + "A " + AnsiColor.RESET);
                    case BOX -> System.out.print(AnsiColor.BLUE + "B " + AnsiColor.RESET);
                    case NPC_HOUSE -> {
                        if (region instanceof VillageRegion vr && vr.getNpcNames().containsKey(pos))
                            System.out.print(AnsiColor.CYAN + vr.getNpcNames().get(pos).charAt(0) + " " + AnsiColor.RESET);
                        else System.out.print(AnsiColor.CYAN + "H " + AnsiColor.RESET);
                    }
                    case SHOP -> {
                        if (region instanceof MarketRegion mr && mr.getShopNames().containsKey(pos))
                            System.out.print(AnsiColor.MAGENTA + mr.getShopNames().get(pos).charAt(0) + " " + AnsiColor.RESET);
                        else System.out.print(AnsiColor.MAGENTA + "S " + AnsiColor.RESET);
                    }
                    default -> System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    private void setupFarms(Map<Player, FarmTemplate> playerChoices) {
        int[][] farmOffsets = {
            {10, 10}, {10, 100}, {100, 10}, {100, 100}
        };

        int i = 0;
        for (Player player : playerChoices.keySet()) {
            FarmTemplate template = playerChoices.get(player);
            FarmRegion farm = new FarmRegion(template);
            int offsetX = farmOffsets[i][0];
            int offsetY = farmOffsets[i][1];
            farm.setOffset(offsetX, offsetY);

            regions.add(farm);
            playerFarms.put(player, farm);

            int cabinX = 3;
            int cabinY = 3;
            int globalX = offsetX + cabinX;
            int globalY = offsetY + cabinY;
            Position playerPos = new Position(globalX, globalY);
            player.setPosition(playerPos);

            Tile playerTile = farm.getTileAt(cabinY, cabinX);
            if (playerTile != null) playerTile.setType(TileType.PLAYER);

            for (int row = 0; row < farm.getFarmHeight(); row++) {
                for (int col = 0; col < farm.getFarmWidth(); col++) {
                    Tile tile = farm.getTileAt(row, col);
                    worldGrid[offsetY + row][offsetX + col] = tile;
                }
            }

            farm.setOwnerName(player.getName());
            i++;
        }
    }

    private void setupVillage(VillageTemplate template) {
        VillageRegion village = new VillageRegion(template);
        village.setOffset(40, 60);
        regions.add(village);

        for (int row = 0; row < village.getFarmHeight(); row++) {
            for (int col = 0; col < village.getFarmWidth(); col++) {
                Tile tile = village.getTileAt(row, col);
                worldGrid[village.getOffsetY() + row][village.getOffsetX() + col] = tile;
            }
        }
    }

    private void setupMarket(MarketTemplate template) {
        MarketRegion market = new MarketRegion(template);
        market.setOffset(90, 60);
        regions.add(market);

        for (int row = 0; row < market.getFarmHeight(); row++) {
            for (int col = 0; col < market.getFarmWidth(); col++) {
                Tile tile = market.getTileAt(row, col);
                worldGrid[market.getOffsetY() + row][market.getOffsetX() + col] = tile;
            }
        }
    }

    // نمیدونم این متد برای چیه؟
    public Region getRegionAt(int x, int y) {
        for (Region region : regions) {
            int ox = region.getOffsetX(), oy = region.getOffsetY();
            if (x >= ox && x < ox + region.getFarmWidth() &&
                y >= oy && y < oy + region.getFarmHeight()) {
                return region;
            }
        }
        return null;
    }

    public boolean isInPlayerCabin(Player player) {
        Tile tile = getTileAt(player.getPosition().getCol(), player.getPosition().getRow());
        return tile != null && tile.getType() == TileType.CABIN;
    }


    public void rebuildWorldGridFromRegions() {
        initializeWorldGrid(); // همه‌ی worldGrid رو با EMPTY پر می‌کنه

        for (Region region : regions) {
            int offsetX = region.getOffsetX();
            int offsetY = region.getOffsetY();

            for (int row = 0; row < region.getFarmHeight(); row++) {
                for (int col = 0; col < region.getFarmWidth(); col++) {
                    Tile regionTile = region.getTileAt(row, col);
                    if (regionTile == null) continue;

                    int worldX = offsetX + col;
                    int worldY = offsetY + row;

                    // اینجا از Region تایل جدید می‌سازیم با position درست و region فعلی
                    Tile newTile = new Tile(new Position(worldX, worldY), region);
                    newTile.setType(regionTile.getType());
                    newTile.setItem(regionTile.getItem());

                    worldGrid[worldY][worldX] = newTile;
                }
            }
        }
    }

    // این متد به چه دردی میخوره؟
    public void restoreDynamicEntities(Game game) {
        for (Player player : game.getPlayers()) {
            Position pos = player.getPosition();
            Tile tile = getTileAt(pos.getCol(), pos.getRow());

            if (tile != null) {
                tile.setType(TileType.PLAYER);
            }
        }

        // اینجا بعداً می‌تونی آیتم‌ها، حیوان‌ها، یا چیزهای دیگه‌ای که در طول بازی اضافه می‌شن رو هم ری‌استور کنی
    }


    public List<Region> getAllRegions() {
        return regions;
    }

    public FarmRegion getPlayerFarm(Player player) {
        return playerFarms.get(player);
    }

    public Tile[][] getWorldGrid() {
        return worldGrid;
    }

    public Map<Player, FarmRegion> getPlayerFarms() {
        return playerFarms;
    }

    public void sendPlayersToHome(Collection<Player> players) {
        for (Player player : players) {
            if (!player.getEnergySystem().isPassedOut()) {
                Position currentPos = player.getPosition();
                Tile currentTile = getTileAt(currentPos.getCol(), currentPos.getRow());
                if (currentTile != null) {
                    currentTile.setType(player.getPreviousTileType());
                }

                FarmRegion farm = getPlayerFarm(player);
                if (farm == null) continue;

                int cabinX = 3;
                int cabinY = 3;
                int globalX = farm.getOffsetX() + cabinX;
                int globalY = farm.getOffsetY() + cabinY;

                Position homePos = new Position(globalX, globalY);
                Tile homeTile = getTileAt(globalX, globalY);

                if (homeTile != null) {
                    player.setPreviousTileType(homeTile.getType());
                    homeTile.setType(TileType.PLAYER);
                }

                player.setPosition(homePos);
            }
        }
    }

    public void generateRandomTreesInFarms() {
        Random random = new Random();
        for (Region region : regions) {
            if (!(region instanceof FarmRegion farm)) continue;

            for (int row = 0; row < farm.getFarmHeight(); row++) {
                for (int col = 0; col < farm.getFarmWidth(); col++) {
                    Tile tile = farm.getTileAt(row, col);


                    if (tile.getType() == TileType.EMPTY && tile.isWalkable()) {
                        if (random.nextDouble() < 0.05) {
                            tile.setType(TileType.TREE);
                        }
                    }
                }
            }
        }
    }

    public void generateRandomForagingItems() {
        Random random = new Random();
        for (Region region : regions) {
            if (!(region instanceof FarmRegion farm)) continue;

            for (int row = 0; row < farm.getFarmHeight(); row++) {
                for (int col = 0; col < farm.getFarmWidth(); col++) {
                    Tile tile = farm.getTileAt(row, col);

                    if (tile.getType() == TileType.EMPTY && tile.isWalkable()) {
                        if (random.nextDouble() < 0.01) {
                            tile.setType(TileType.FORAGING);
                        }
                    }
                }
            }
        }
    }

    public void updateQuarryStonesDaily() {
        Random random = new Random();
        for (Region region : regions) {
            if (!(region instanceof FarmRegion farm)) continue;

            for (int row = 0; row < farm.getFarmHeight(); row++) {
                for (int col = 0; col < farm.getFarmWidth(); col++) {
                    Tile tile = farm.getTileAt(row, col);

                    if (tile.getType() == TileType.ROCK) {
                        tile.setType(TileType.QUARRY);
                    } else if (tile.getType() == TileType.QUARRY && random.nextDouble() < 0.20) {
                        tile.setType(TileType.ROCK);
                    }
                }
            }
        }
    }

    public boolean isInFarm(int x, int y) {
        Tile tile = getTileAt(x, y);
        return tile != null && tile.getRegion() instanceof FarmRegion;
    }

    public boolean isInShop(int x, int y) {
        Tile tile = getTileAt(x, y);
        return tile != null && tile.getRegion() instanceof MarketRegion &&
            tile.getType() == TileType.SHOP;
    }

    public boolean isInVillage(int x, int y) {
        Tile tile = getTileAt(x, y);
        return tile != null && tile.getRegion() instanceof VillageRegion;
    }

    public String getFarmOwnerNameAt(int x, int y) {
        Region region = getRegionAt(x, y);
        if (region instanceof FarmRegion farm) {
            return farm.getOwnerName();
        }
        return null;
    }

    public String getShopNameAt(int x, int y) {
        Tile tile = getTileAt(x, y);
        System.out.println(tile);
        if (tile != null && tile.getRegion() instanceof MarketRegion market) {
            return market.getShopNames().get(tile.getPosition());
        }
        return null;
    }

    public String getNpcNameAt(int x, int y) {
        Tile tile = getTileAt(x, y);
        if (tile != null && tile.getRegion() instanceof VillageRegion village) {
            return village.getNpcNames().get(tile.getPosition());
        }
        return null;
    }

//    // متدی برای شخم زدن
//    public static boolean tillSoil(int x, int y) {
//        Game game = GameManager.getCurrentGame();
//        WorldMap map = game.getWorldMap();
//        Tile tile = map.getTileAt(x, y);
//        if (tile == null) return false;
//
//        if (tile.getType() == TileType.EMPTY) {
//            tile.setType(TileType.TILLED_SOIL);
//            return true;
//        }
//        return false;
//    }

    //    public static void plantCrop(int x, int y) {
//        Game game = GameManager.getCurrentGame();
//        WorldMap map = game.getWorldMap();
//        Tile tile = map.getTileAt(x, y);
//        if (tile == null)
//
//        if (tile.getType() == TileType.TILLED_SOIL) {
//            tile.setType(TileType.CROP);
//        }
//    }
    public static void strikeLightningAt(int x, int y) {
        WorldMap map = GameManager.getCurrentGame().getWorldMap();
        Tile tile = map.getTileAt(x, y);
        if (tile == null) return;

        TileType type = tile.getType();
        if (type == TileType.CROP) {
            tile.setType(TileType.EMPTY);
        } else if (type == TileType.TREE) {
            tile.setType(TileType.CHARCOAL);
        } else if (type == TileType.ANIMAL) {
            tile.setType(TileType.EMPTY);
        }
    }


    // متد برای تغییر نوع کاشی ها
    public boolean replaceTileTypeIfMatch(int x, int y, TileType expected, TileType replacement) {
        Tile tile = getTileAt(x, y);
        if (tile != null && tile.getType() == expected) {
            tile.setType(replacement);
            return true;
        }
        return false;
    }

    //ببین کلا باید یه متد بزنیم دایرکشن مورد نظر رو تفسیر کنه چیه و بعدش کاشی اون مختصات رو برگردونه
    // ورودی این متد باید String باشه.


    public Tile getTileAt(int x, int y) {
        if (x < 0 || y < 0 || x >= WORLD_WIDTH || y >= WORLD_HEIGHT)
            return null;
        return worldGrid[y][x];
    }

    public boolean isNear(Position pos, TileType targetType) {
        for (Position adj : pos.getAdjacentPositions()) {
            Tile t = getTileAt(adj.getRow(), adj.getCol());
            if (t != null && t.getType() == targetType) return true;
        }
        return false;
    }


    public List<Position> getAdjacentPositions(Position pos) {
        List<Position> adjacents = new ArrayList<>();
        int[][] deltas = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] d : deltas) {
            int newRow = pos.getRow() + d[0];
            int newCol = pos.getCol() + d[1];
            adjacents.add(new Position(newRow, newCol));
        }

        return adjacents;
    }


    public Position getNPCHomePosition(String npcName) {

        for (Region region : regions) {
            if (region instanceof VillageRegion village) {
                for (Map.Entry<Position, String> entry : village.getNpcNames().entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(npcName)) {
                        Position local = entry.getKey();
                        return getGlobalPosition(village, local);
                    }
                }
            }
        }
        return null;
    }

    public static Position getGlobalPosition(Region region, Position localPos) {
        return region.getGlobalPosition(localPos);
    }

    public static boolean isPlayerNearNpc(Player player, String npcName) {
        return true;
    }
}
