package io.github.some_example_name.controllers;


import io.github.some_example_name.model.Animal.Animal;
import io.github.some_example_name.model.Animal.FishCreator;
import io.github.some_example_name.model.NPC.*;
import io.github.some_example_name.model.Plant.Enums.CropType;
import io.github.some_example_name.model.Plant.Enums.TreeType;
import io.github.some_example_name.model.Plant.PlantFactory;
import io.github.some_example_name.model.Plant.PlantInstance;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.Tools.FishingPole;
import io.github.some_example_name.model.Tools.Tool;
import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.Weather.Weather;
import io.github.some_example_name.model.artisan.ArtisanManager;
import io.github.some_example_name.model.artisan.ArtisanRecipes;
import io.github.some_example_name.model.artisan.ArtisanTask;
import io.github.some_example_name.model.crafting.CraftingManager;
import io.github.some_example_name.model.crafting.CraftingRecipe;
import io.github.some_example_name.model.enums.*;
import io.github.some_example_name.model.farm.DefaultFarmTemplate;
import io.github.some_example_name.model.farm.FarmRegion;
import io.github.some_example_name.model.farm.FarmTemplate;
import io.github.some_example_name.model.farm.LakeFarmTemplate;
import io.github.some_example_name.model.game.*;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.items.ItemFactory;
import io.github.some_example_name.model.market.ClassicMarketTemplate;
import io.github.some_example_name.model.market.MarketTemplate;
import io.github.some_example_name.model.region.MarketRegion;
import io.github.some_example_name.model.shop.Shop;
import io.github.some_example_name.model.Player.inventory.Inventory;
import io.github.some_example_name.model.shop.ShopType;
import io.github.some_example_name.model.user.UserManager;
import io.github.some_example_name.model.village.ClassicVillageTemplate;
import io.github.some_example_name.model.village.VillageTemplate;
import io.github.some_example_name.model.game.GameManager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.some_example_name.model.game.Game.getShopManager;

import static io.github.some_example_name.model.game.GameManager.addActivePlayer;

import static io.github.some_example_name.model.game.Tile.artisanTileMap;

public class GameMenuController {

    public static void handleNewGame(String command, Scanner scanner) {
        Matcher matcher = MenuCommands.START_NEW_GAME.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("at least one username is required.");
            return;
        }

        String creator = UserManager.getCurrentUser().getUsername();
        String[] inputUsernames = matcher.group("users").trim().split("\\s+");


        if (inputUsernames.length == 0) {
            System.out.println("at least one username is required.");
            return;
        }

        if (inputUsernames.length > 3) {
            System.out.println("You can only invite 1 to 3 players.");
            return;
        }

        Set<String> allPlayers = new LinkedHashSet<>();


        if (GameManager.isUserInAnyGame(creator)) {
            System.out.println("You are already in a game, You cant make a new game...!\nYou can load your previous " +
                "game or login with a new account.");
            return;
        }

        for (String username : inputUsernames) {
            if (GameManager.isUserInAnyGame(username)) {
                System.out.println("User already in another game you can not make a new game with this user : "
                    + username);
                return;
            }
            addActivePlayer(creator);
            allPlayers.add(creator);
            addActivePlayer(username);
            allPlayers.add(username);
        }


        Game game = new Game(new ArrayList<>(allPlayers), creator);
        GameManager.createGame(new ArrayList<>(allPlayers), creator);
        GameManager.setCurrentGame(game);

        Player stater = game.getCurrentPlayerForPlay();
        game.setCurrentPlayer(stater);


        System.out.println("Game created with players: " + allPlayers);
        System.out.println("Players will now select their maps (use: game map <number>)");

        handleMapSelection(scanner, game);
    }

    public static void handleMapSelection(Scanner scanner, Game game) {
        List<String> usernames = game.getUsernames();

        int currentIndex = 0;
        while (currentIndex < usernames.size()) {
            String username = usernames.get(currentIndex);
            Player currentPlayer = game.getPlayer(username);

            System.out.println(currentPlayer.getName() + ", choose your map (1 or 2):");

            String input = scanner.nextLine().trim();
            Matcher matcher = Pattern.compile("game map (?<num>\\d+)").matcher(input);

            if (!matcher.matches()) {
                System.out.println("invalid command. Use: game map <1 or 2>");
                continue;
            }

            int mapNum = Integer.parseInt(matcher.group("num"));
            if (mapNum != 1 && mapNum != 2) {
                System.out.println("only map numbers 1 or 2 allowed.");
                continue;
            }

            FarmTemplate template = mapNum == 1 ? new LakeFarmTemplate() : new DefaultFarmTemplate();

            game.setMap(currentPlayer, mapNum);
            game.setTemplate(currentPlayer, template);

            System.out.println("Map " + mapNum + " selected for " + currentPlayer.getName());
            currentIndex++;
        }

        System.out.println(" All players have selected maps. WorldMap is about to build...");
        CraftingManager.initializeRecipes();
        Map<Player, FarmTemplate> templates = game.getFinalTemplates();


        VillageTemplate villageTemplate = new ClassicVillageTemplate();
        MarketTemplate marketTemplate = new ClassicMarketTemplate();

        WorldMap worldMap = new WorldMap(templates, villageTemplate, marketTemplate);

        for (Player player : templates.keySet()) {
            FarmRegion farm = worldMap.getPlayerFarm(player);
            Position localCabinPos = findCabinCenter(farm);
            int globalX = farm.getOffsetX() + localCabinPos.getRow();
            int globalY = farm.getOffsetY() + localCabinPos.getCol();
            player.setPosition(new Position(globalX, globalY));
        }

        game.setWorldMap(worldMap);

        System.out.println(" WorldMap created:");

    }

    private static Position findCabinCenter(FarmRegion farm) {
        for (int y = 0; y < farm.getFarmHeight(); y++) {
            for (int x = 0; x < farm.getFarmWidth(); x++) {
                if (farm.getTileAt(y, x).getType() == TileType.CABIN) {
                    return new Position(x + 1, y + 1);
                }
            }
        }
        return new Position(1, 1);
    }

    public static void printMap(String command, Scanner scanner) {
        Matcher matcher = MenuCommands.PRINT.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        int size = Integer.parseInt(matcher.group("size"));
        Game game = GameManager.getCurrentGame();
        if (game == null) {
            System.out.println("there is no game!");
            return;
        }
        WorldMap map = game.getWorldMap();
        map.printMapSegment(x, y, size);
    }

    public static void handleNextTurn() {
        Game game = GameManager.getCurrentGame();
        game.advanceTurn();
        Player player = game.getCurrentPlayerForPlay();
        player.setMaxEnergy();

        Player current = game.getCurrentPlayerForPlay();
        if (current.getEnergySystem().isPassedOut()) {
            System.out.println(current.getName() + ", you have passed out you can not play until tomorrow!");
            handleNextTurn();
            return;
        }
        game.setCurrentPlayer(current);
        System.out.println("🔄 It's now " + current.getName() + "'s turn.");
    }

    public static void handleWalkCommand(String command) {
        Matcher matcher = MenuCommands.WALK.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("Invalid command format.");
            return;
        }

        int targetX = Integer.parseInt(matcher.group("x"));
        int targetY = Integer.parseInt(matcher.group("y"));

        Game game = GameManager.getCurrentGame();
        if (game == null) {
            System.out.println("there is no game.");
            return;
        }
        Player player = game.getCurrentPlayerForPlay();
        Position start = player.getPosition();
        Position goal = new Position(targetY, targetX);

        WorldMap map = game.getWorldMap();
        Tile destinationTile = map.getTileAt(targetX, targetY);
        if (destinationTile == null) {
            System.out.println("Destination is outside the map!");
            return;
        }

        Region destRegion = destinationTile.getRegion();
        if (destRegion instanceof FarmRegion destFarm) {
            FarmRegion playerFarm = map.getPlayerFarm(player);
            if (playerFarm != destFarm) {
                System.out.println("You can't enter another player's farm!");
                return;
            }
        }

        if (!destinationTile.isWalkable()) {
            System.out.println("Destination is not walkable.");
            return;
        }
        //Tile destTile = map.getTileAt(targetX, targetY);
        //Region destRegion = destTile.getRegion();


        if (destRegion instanceof MarketRegion market) {
            String shopName = market.getShopNames().get(destinationTile.getPosition());
            if (shopName != null) {
                int currentHour = DateAndTime.getHour();

//                int[] hours = ShopHours.SHOP_TIMES.getOrDefault(shopName, new int[]{0, 24});
//                int open = hours[0];
//                int close = Math.min(hours[1], 22);
//
//                if (currentHour < open || currentHour >= close) {
//                    System.out.println("❌ " + shopName + " is closed now! Open hours: " + open + ":00 to " + close + ":00");
//                    return;
//                }
            }
        }


        List<Position> path = Pathfinder.findPath(map, start, goal);
        if (path == null) {
            System.out.println(" No path found to that location.");
            return;
        }
        if (destinationTile.getType() == TileType.PLAYER) {
            System.out.println(" Another player is already standing there!");
            return;
        }

        // منطقه‌هایی که عبور کردیم ازشون (برای energy bonus)
        Set<Region> regionsPassed = new HashSet<>();
        int bonusEnergy = 0;

        // پیاده‌روی گام به گام:
        for (int i = 1; i < path.size(); i++) {
            Position nextPos = path.get(i);
            Tile nextTile = map.getTileAt(nextPos.getCol(), nextPos.getRow());
            Region region = nextTile.getRegion();

            if (region != null && !regionsPassed.contains(region)) {
                bonusEnergy++;
                regionsPassed.add(region);
            }

            double energyCost = 1.0 / 20 + bonusEnergy;
            bonusEnergy = 0;


            double consumed = player.getEnergySystem().consume(energyCost);

            if (consumed < energyCost) {
                System.out.println("😵 You passed out while walking at " + path.get(i - 1));
                break;
            }

            Position prevPos = player.getPosition();
            Tile prevTile = map.getTileAt(prevPos.getCol(), prevPos.getRow());
            prevTile.setType(player.getPreviousTileType());

            player.setPreviousTileType(nextTile.getType());
            nextTile.setType(TileType.PLAYER);
            player.setPosition(nextPos);
        }

        System.out.println("✅ Walk finished at " + player.getPosition() +
            ". Energy left: " + player.getEnergy());
    }

    public static void handleExitGame() {
        Game game = GameManager.getCurrentGame();
        Player current = game.getCurrentPlayerForPlay();

        if (!game.getCreatorUsername().equals(current.getName())) {
            System.out.println(" Only the game creator can exit and save the game.");
            return;
        }

       // GameSaveManager.saveGame(game);
        System.out.println("💾 Game saved. You have exited the game.");
    }

    public class Pathfinder {
        public static List<Position> findPath(WorldMap map, Position start, Position goal) {
            Queue<Position> queue = new LinkedList<>();
            Map<Position, Position> cameFrom = new HashMap<>();
            Set<Position> visited = new HashSet<>();

            queue.add(start);
            visited.add(start);
            cameFrom.put(start, null);
            //System.out.println(start);

            int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1};
            int[] dy = {0, 0, -1, 1, -1, -1, 1, 1};

            while (!queue.isEmpty()) {
                Position current = queue.poll();

                if (current.equals(goal)) {
                    return reconstructPath(cameFrom, current);
                }

                for (int i = 0; i < dx.length; i++) {
                    int nx = current.getCol() + dx[i];
                    int ny = current.getRow() + dy[i];
                    Position neighbor = new Position(ny, nx);

                    if (visited.contains(neighbor)) continue;

                    Tile tile = map.getTileAt(nx, ny);
                    if (tile == null || !tile.isWalkable()) continue;
                    //System.out.println("Visited " + neighbor + " - " + tile.getType());

                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                    queue.add(neighbor);
                }
            }

            return null;
        }

        private static List<Position> reconstructPath(Map<Position, Position> cameFrom, Position current) {
            LinkedList<Position> path = new LinkedList<>();
            while (current != null) {
                path.addFirst(current);
                current = cameFrom.get(current);
            }
            return path;
        }
    }

    public static void enterHomeMenu(Scanner scanner) {
        Game game = GameManager.getCurrentGame();
        WorldMap map = game.getWorldMap();
        Player player = game.getCurrentPlayerForPlay();
        if (map.isInPlayerCabin(player)) {
            HomeMenu.show(player, scanner);
        } else {
            System.out.println("You are not in your home,You can not enter the home menu.!");
        }
    }

    public static void handleLoadGameCommand() {
        String username = UserManager.getCurrentUser().getUsername();
        //Game loadedGame = GameSaveManager.loadGame(username);
//        if (loadedGame != null) {
//            GameManager.setCurrentGame(loadedGame);
//            loadedGame.getWorldMap().rebuildWorldGridFromRegions();
//            loadedGame.getWorldMap().restoreDynamicEntities(loadedGame);
//            System.out.println("🎮 Game successfully loaded for " + username + ".");
//        } else {
//            System.out.println("❌ No saved game found for you.");
//        }
    }

    public static void showTime() {
        int hour = DateAndTime.getHour();
        System.out.println("The hour is " + hour + ".");
    }

    public static void showDate() {
        int day = DateAndTime.getTotalDays();
        System.out.println(day + " day(s) has been passed from the time that you started the game.");
    }

    public static void showDateAndTime() {
        int hour = DateAndTime.getHour();
        int day = DateAndTime.getTotalDays();
        System.out.println(hour + " hour(s) has been passed after the " + day + "day that you started the game.");
    }

    public static void showDayOfTheWeek() {
        DateAndTime.displayDayOfWeek();
    }

    public static void showSeason() {
        System.out.println("we are now in " + DateAndTime.getCurrentSeason() + " season .");
    }

    public static void cheatTime(String command) {
        Matcher matcher = MenuCommands.CHEAT_TIME.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        int hoursToAdd = Integer.parseInt(matcher.group("X"));
        //System.out.println("here?");
        System.out.println(hoursToAdd);
        DateAndTime.cheatHours(hoursToAdd);
    }

    public static void cheatDays(String command) {
        Matcher matcher = MenuCommands.CHEAT_DAY.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        int daysToAdd = Integer.parseInt(matcher.group("X"));
        DateAndTime.cheatDays(daysToAdd);
    }

    public static void showWeather() {
        Weather.getToday();
    }

    public static void getTomorrowWeather() {
        Weather.getTomorrow();
    }

    public static void craftingShowRecipes() {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        HomeMenu.showAvailableRecipes(player);
    }

    public static void handleCraftCommand(String command) {
        Matcher matcher = MenuCommands.CRAFTING_CRAFT.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("Invalid format. Use: crafting craft <itemName>");
            return;
        }

        String itemName = matcher.group("itemName");
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        WorldMap map = game.getWorldMap();
        if (!map.isInPlayerCabin(player)) {
            System.out.println("You must be in your cabin to craft.");
            return;
        }

        if (!CraftingManager.recipeExists(itemName)) {
            System.out.println("No such recipe exists.");
            return;
        }

        CraftingRecipe recipe = CraftingManager.getRecipe(itemName);


        if (!recipe.isUnlockedFor(player)) {
            System.out.println("You haven't unlocked this recipe yet.");
            return;
        }

        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            String name = entry.getKey();
            int required = entry.getValue();
            if (!player.getInventory().hasItem(name, required)) {
                System.out.println(" Not enough " + name + ". Needed: " + required);
                return;
            }
        }

        if (player.getInventory().getRemainingCapacity() <= 0 &&
            !player.getInventory().hasItem(itemName)) {
            System.out.println("❌ Inventory is full. Cannot add new item type.");
            return;
        }


        int energyCost = recipe.getEnergyCost();
//        if (player.getEnergy() < energyCost) {
//            System.out.println("❌ Not enough energy to craft.");
//            return;
//        }


        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            player.getInventory().removeItem(entry.getKey(), entry.getValue());
        }

        // Item crafted = ItemFactory.create(itemName);
        // TODO: پیاده‌سازی یا ماک این
        //player.getInventory().addItem(crafted);
        player.decreaseEnergy(energyCost);

        System.out.println("✅ Crafted " + itemName + "! Energy left: " + player.getEnergy());
    }

    public static void handleCheatAddItemCommand(String command) {
        Matcher matcher = MenuCommands.CHEAT_ADD_ITEM.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("Invalid format. Use: cheat add item -n <name> -c <count>");
            return;
        }

        String name = matcher.group("name");
        int count = Integer.parseInt(matcher.group("count"));

        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        for (int i = 0; i < count; i++) {

            ItemFactory.createItem(name, player.getInventory());

        }
//        for (int i = 0; i < count; i++) {
//            Item item = ItemFactory.create(name); // یا خودت بساز
//            Result res = player.getInventory().addItem(item);
//            if (!res.isSuccess()) {
//                System.out.println("❌ Failed to add item: " + res.getMessage());
//                return;
//            }
//        }

        System.out.println("✅ Added " + count + " x " + name + " to inventory.");
    }

    public static void handlePlaceCommand(String command) {
        Matcher matcher = MenuCommands.PLACE_ITEM.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("❌ Invalid command format.");
            return;
        }

        String itemName = matcher.group("name").trim().toLowerCase();
        String direction = matcher.group("dir").trim().toLowerCase();

        Game game = GameManager.getCurrentGame();
        if (game == null) {
            System.out.println("❌ No active game.");
            return;
        }

        Player player = game.getCurrentPlayerForPlay();
        Inventory inventory = player.getInventory();

        if (!inventory.hasItem(itemName)) {
            System.out.println("❌ You don't have this item in your inventory.");
            return;
        }


        Position pos = player.getPosition();
        Position targetPos = pos.getAdjacent(direction);
        if (targetPos == null) {
            System.out.println("❌ Invalid direction.");
            return;
        }

        Tile targetTile = game.getWorldMap().getTileAt(targetPos.getCol(), targetPos.getRow());
//        if (targetTile == null || targetTile.getType() != TileType.EMPTY) {
//            System.out.println("❌ Cannot place item on non-empty tile.");
//            return;
//        }


        TileType artisanType = artisanTileMap.get(itemName);
        if (artisanType != null) {
            targetTile.setType(artisanType);
            inventory.removeItem(itemName, 1);
            System.out.println("✅ " + itemName + " placed successfully as " + artisanType.name());
            return;
        }

        System.out.println("❌ This item cannot be placed.");
    }

    public static void handleArtisanUseCommand(String command) {
        Matcher matcher = Pattern.compile("artisan use (?<artisan>[a-zA-Z_]+)( (?<item1>[a-zA-Z ]+))?( (?<item2>[a-zA-Z ]+))?( (?<item3>[a-zA-Z ]+))?( (?<item4>[a-zA-Z ]+))?( (?<item5>[a-zA-Z ]+))?").matcher(command.trim());
        if (!matcher.matches()) {
            System.out.println("❌ Invalid artisan use command format.");
            return;
        }

        String artisanName = matcher.group("artisan").toLowerCase();
        Player player = GameManager.getCurrentGame().getCurrentPlayerForPlay();
        WorldMap map = GameManager.getCurrentGame().getWorldMap();
        Position playerPos = player.getPosition();


        Position target = null;
        for (Position adj : playerPos.getAdjacentPositions()) {
            Tile tile = map.getTileAt(adj.getCol(), adj.getRow());
            if (tile != null && tile.getType().name().equalsIgnoreCase(artisanName)) {
                target = adj;
                break;
            }
        }

//        if (target == null) {
//            System.out.println("❌ No " + artisanName + " around you.");
//            return;
//        }


        if (!ArtisanRecipes.isArtisanValid(artisanName)) {
            System.out.println("❌ Unknown artisan device: " + artisanName);
            return;
        }


        List<String> inputs = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String item = matcher.group("item" + i);
            if (item != null) inputs.add(item.trim().toLowerCase());
        }

        ArtisanRecipes.Recipe recipe = ArtisanRecipes.findRecipe(artisanName, inputs);
        if (recipe == null) {
            System.out.println("❌ No recipe found for " + artisanName + " with given inputs.");
            return;
        }


//        for (String item : recipe.inputs) {
//            if (!player.getInventory().hasItem(item)) {
//                System.out.println("❌ You don’t have required input: " + item);
//                return;
//            }
//        }


        for (String item : recipe.inputs) {
            player.getInventory().removeItem(item, 1);
        }


        player.decreaseEnergy(recipe.energy);


        int readyAtHour = DateAndTime.getHour() + recipe.durationInHours;
        ArtisanManager.registerInProgress(target, artisanName, player.getName(), readyAtHour, recipe.output);
        System.out.println("✅ Processing started. Output will be ready in " + recipe.durationInHours + " hours.");
    }

    public static void handleArtisanGetCommand(String command) {
        Matcher matcher = Pattern.compile("artisan get (?<artisan>[a-zA-Z_]+)").matcher(command.trim());
        if (!matcher.matches()) {
            System.out.println("❌ Invalid artisan get command format.");
            return;
        }

        String artisanName = matcher.group("artisan").toLowerCase();
        Player player = GameManager.getCurrentGame().getCurrentPlayerForPlay();
        WorldMap map = GameManager.getCurrentGame().getWorldMap();
        Position playerPos = player.getPosition();

        Position target = null;
        for (Position adj : playerPos.getAdjacentPositions()) {
            Tile tile = map.getTileAt(adj.getCol(), adj.getRow());
            if (tile != null && tile.getType().name().equalsIgnoreCase(artisanName)) {
                target = adj;
                break;
            }
        }

//        if (target == null) {
//            System.out.println("❌ No " + artisanName + " device around you.");
//            return;
//        }

        if (!ArtisanManager.isReady(target)) {
            System.out.println("⏳ Item is not ready yet.");
            return;
        }

        ArtisanTask task = ArtisanManager.getTaskAt(target);


        ArtisanManager.removeTask(target);
        System.out.println("✅ You collected the item ");
    }


    public static void cheatWeather(String command) {
        Matcher matcher = MenuCommands.CHEAT_FORECAST.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        WeatherType type = WeatherType.valueOf(matcher.group("Type"));
        Weather.cheatSetTomorrowWeather(type);
    }

    public static void showEnergy() {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        System.out.println("Your energy is : " + player.getEnergy());
    }

    public static void setEnergy(String command) {
        Matcher matcher = MenuCommands.SET_ENERGY.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        int value = Integer.parseInt(matcher.group("value"));
        Game game = GameManager.getCurrentGame();
        if (game == null) {
            System.out.println("there is no game for you!");
            return;
        }
        Player player = game.getCurrentPlayerForPlay();
        player.setEnergy(value);
    }

    public static void setUnlimited() {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        player.setUltimate();
    }

    public static void printMapHelp() {
        System.out.println("""
            Map Legend:
            . = Empty
            C = Cabin
            G = Greenhouse
            L = Lake
            Q = Quarry
            # = Border
            @ = Player
            R = Rock
            T = Tree
            F = Foraging
            t = Tilled Soil
            c = Crop
            """);
    }

    public static void handleCheatThor(String command) {
        Matcher matcher = MenuCommands.START_NEW_GAME.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        WorldMap map = GameManager.getCurrentGame().getWorldMap();
        map.strikeLightningAt(x, y);

        System.out.println("⚡ Thunder struck at (" + x + ", " + y + ")!");
    }

    public static void command(String command) {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        Shop shop = getCurrentShopForPlayer(player);
        String[] parts = command.trim().split("\\s+");
        switch (parts[0].toLowerCase()) {
            case "craftinfo":
                if (parts.length < 3 || !parts[1].equals("-n")) {
                    System.out.println("Usage: craftinfo -n <name>");
                    break;
                }
                String inputName = parts[2];
                //PlantController.getInfo(inputName);
                break;
            case "tools":
                if (Objects.equals(parts[1], "show") && Objects.equals(parts[2], "current")) {
                    System.out.println(ToolController.showCurrentTool(player));
                } else if (Objects.equals(parts[1], "show") && Objects.equals(parts[2], "available")) {
                    System.out.println(ToolController.showAvailableTools(player));
                } else if (Objects.equals(parts[1], "equip")) {
                    String toolName = parts[2];
                    System.out.println(ToolController.handleEquip(toolName, player));
                } else if (Objects.equals(parts[1], "upgrade")) {
                    String toolName = parts[2];
                    if (shop == null) {
                        System.out.println("You are not inside a valid shop.");
                        break;
                    }
                    System.out.println(ShopController.upgradeTools(parts, shop, player));
                } else if (Objects.equals(parts[1], "use")) {
                    int x = Integer.parseInt(parts[3]);
                    int y = Integer.parseInt(parts[4]);
                    ToolController.handleUse(x, y, player);
                }
                break;
            case "show":
                if (parts.length >= 3 && parts[1].equals("all")) {
                    if (shop == null) {
                        System.out.println("You are not inside a valid shop.");
                        break;
                    }
                    if (parts[2].equals("products")) {
                        System.out.println(ShopController.showAllProducts(shop));
                    } else if (parts[2].equals("available") && parts.length >= 4 && parts[3].equals("products")) {
                        System.out.println(ShopController.showAvailableProducts(shop));
                    } else {
                        System.out.println("Invalid command");
                    }
                }
                if (parts[1].equals("animals")) {
                    AnimallController.showAnimals(player);
                } else {
                    System.out.println("Invalid command");
                }
                if (parts[1].equals("barnanimal")) {
                    AnimallController.showbarnAnimalCategories(player);
                } else if (parts[1].equals("coopanimals")) {
                    AnimallController.showCagedAnimalCategories(player);
                }
                break;
            case "purchase":
                if (shop == null) {
                    System.out.println("You are not inside a valid shop.");
                    break;
                }
                Result.success(ShopController.handlePurchase(parts, shop, player));
                break;
            case "build":
                if (shop == null) {
                    System.out.println("You are not inside a valid shop.");
                    break;
                }
                System.out.println(ShopController.handleBuild(parts, shop, player));
                break;
            case "buy":
                if (parts[1].equals("animal")) {
                    String animal = parts[3];
                    String name = parts[5];
                    System.out.println(ShopController.handleBuyAnimal(parts, shop, player));
                }
            case "cheat":
                if (parts.length == 4 && parts[1].equals("add") && parts[3].equals("dollars")) {
                    try {
                        int amount = Integer.parseInt(parts[2]);
                        System.out.println(ShopController.cheatAddMoney(amount, player));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount for money.");
                    }
                }
                if (parts[1].equals("-a")) {
                    int x = Integer.parseInt(parts[2]);
                    player.addMoney(x);
                    System.out.println("added money to " + player.getName());
                }
                if (parts[1].equals("animal frindship")) {
                    String name = parts[3];
                    int amount = Integer.parseInt(parts[4]);
                    Animal animal = player.getBroughtAnimal().get(name);
                    AnimallController.addFriendship(amount, animal);
                }
                if (parts[1].equals("level")) {

                    String receiverUsername = parts[2];

                    String a = parts[3];

                    int amount = Integer.parseInt(parts[4]);

                    FriendshipController.increacrLevel(amount, receiverUsername, a);

                }
                break;
            case "inventory":
                if (parts[1].equals("show")) {
                    System.out.println(ToolController.showInventory(player));
                } else if (parts[1].equals("trash")) {
                    String itemName = parts[3];
                    int quantity = 1;
                    if (parts.length > 5) {
                        try {
                            quantity = Integer.parseInt(parts[5]);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid quantity!");
                            return;
                        }
                    }
                    System.out.println(ToolController.trashItem(player, itemName, quantity));
                }else if (parts[1].equals("money")) {

                    System.out.println(player.getMoney());

                }
                break;
//            case "plant":
//                WorldMap worldMap = GameManager.getCurrentGame().getWorldMap();
//                if(player.getEquippedItem().getName().contains("Basic_Hoe")) {
//                    Tool currentTool = (Tool) player.getEquippedItem();
//                    if (parts[1].equals("-M")) {
//                        int x = Integer.parseInt(parts[6]);
//                        int y = Integer.parseInt(parts[7]);
//                        if(worldMap.getTileAt(x,y).getType() != TileType.TILLED_SOIL) {
//                            System.out.println("there isn't a tile soil");
//                        }
//                        String seedName = parts[2];
//                        CropType crop = PlantController.handleMixedSeedPlanting(seedName);
//
//                        if (crop != null ) {
//                            PlantInstance plant = PlantFactory.createPlant(crop);
//                            worldMap.replaceTileTypeIfMatch(x,y,TileType.TILLED_SOIL,TileType.CROP);
//                            player.addPlantedInfo(x, y, plant);
//                            currentTool.use(player, x, y);
//                            player.getInventory().removeItem(seedName, 1);
//                            System.out.println("Planted " + crop.getName() + " at (" + x + "," + y + ")");
//                        }else{
//                            System.out.println("Mix seed name is unvalide");
//                        }
//                    }else{
//                        String seedName = parts[2];
//                        int x = Integer.parseInt(parts[4]);
//                        int y = Integer.parseInt(parts[5]);
//                        if(worldMap.getTileAt(x,y).getType() != TileType.TILLED_SOIL) {
//                            System.out.println("there isn't a tile soil");
//                        }
//                        TreeType tree = TreeType.findBySeedName(seedName);
//                        CropType crop =CropType.findBySeedName(seedName);
//                        if(crop!=null){
//                            PlantInstance plant;
//                            if (crop.getRegrowthTime() != null && crop.getRegrowthTime() > 0) {
//                                plant = new RegrowableCropInstance(crop);
//                            } else {
//                                plant = PlantFactory.createPlant(crop);
//                            }
//                            currentTool.use(player, x, y);
//
//                            player.addPlantedInfo(x, y, plant);
//                            //PlantController.plantSeed(seedName,x,y);
//                            System.out.println("Planted " + crop.getName() + " at (" + x + "," + y + ")");}
//                        if(tree!=null){
//                            PlantInstance plant = PlantFactory.creatTree (tree);
//                            currentTool.use(player, x, y);
//                            player.addPlantedInfo(x, y, plant);
//                            //PlantController.plantSeed(seedName,x,y);
//                            System.out.println("Planted " + tree.getName() + " at (" + x + "," + y + ")");
//                        }
//                    }
//                }else{
//                    System.out.println("you dont have hoe for plant.");
//                }
//                break;
//            case "collect":
//                int x = Integer.parseInt(parts[4]);
//                int y = Integer.parseInt(parts[5]);
//                if(player.getEquippedItem().getName().contains("scythe")){
//                    Tool currentTool = (Tool) player.getEquippedItem();
//                    currentTool.use(player,x,y);
//                    PlantingBehavior beh = PlantController.detectBehaviorAt(x, y);
//                    System.out.println(beh.toString());
//                    switch (beh) {
//                        case ONE_TIME_CROP:
//                            OneTimeCropInstance otc = (OneTimeCropInstance) player.getPlantedAt(x,y);
//                            boolean ok1 = PlantController.testOneTimeCropLifecycle(otc);
//                            if(ok1){
//                                WorldMap worldmap = GameManager.getCurrentGame().getWorldMap();
//                                        player.removePlantedInfo(x,y);
//                                worldmap.replaceTileTypeIfMatch(x,y,TileType.CROP,TileType.EMPTY);
//                                System.out.println(player.getSkill("Farming").gainXp(5));
//                            }
//                            System.out.println("collect crop sucssefuly.");
//                            break;
//
//                        case REGROWABLE_CROP:
//                            RegrowableCropInstance rc = (RegrowableCropInstance) player.getPlantedAt(x,y);
//                            boolean ok2 = PlantController.testRegrowableCropLifecycle(rc);
//                            if(ok2){
//                                System.out.println("collectted regrowable crop sucssefuly.");
//                            }
//                            break;
//
//                        case TREE:
//                            Tree tree = player.getPlantedTreeAt(x,y);
//                            if (tree == null) {
//                                System.out.println("No tree planted at ("+x+","+y+").");
//                                break;
//                            }
//                            boolean fruitOk = PlantController.testPeriodicFruit(tree);
//                            if(fruitOk){
//                                System.out.println("collected fruit of this sucssefuly.");
//                            }
//                            boolean lightningOk = PlantController.testLightningStrike(tree);
//                            if(lightningOk){
//                                System.out.println("burned Tree");
//                                player.removePlantedInfo(x,y);
//                                player.removePlantedTreeAt(x,y);
//                            }
//                        default:
//                            System.out.println(String.format("No plant planted at (%d,%d).", x, y));}
//
//                }else if(parts[1].equals("product")){
//                    String name = parts[2];
//                    Animal b=player.getBroughtAnimal().get(name);
//                    b.collectProduct(b);
//                    System.out.println(" collected product "+b.getName());
//                } else{
//                    System.out.println("you dont have scythe for collect.");
//                }
//                break;
//            case "cut":
//                int w = Integer.parseInt(parts[2]);
//                int z = Integer.parseInt(parts[3]);
//                if(player.getEquippedItem().getName().contains("axe")){
//                    Tool currentTool = (Tool) player.getEquippedItem();
//                    currentTool.use(player,w,z);
//                    Tree tree = player.getPlantedTreeAt(w,z);
//                    tree.cutDown();
//                }else {System.out.println("you dont have axe for cut.");}
//                break;
//            case "fertilize":
//                System.out.println(command);
//                String fertilizerName = parts[2];
//                int r = Integer.parseInt(parts[4]);
//                int t = Integer.parseInt(parts[5]);
//                PlantInstance plant = player.getPlantedAt(r,t);
//                    PlantController.fertilizeTile( fertilizerName,r,t);
//                break;
//            case "showplant":
//                int u = Integer.parseInt(parts[1]);
//                int i = Integer.parseInt(parts[2]);
//                //PlantController.showPlant(u,i);
//                break;
//                case "showgrowthplant":
//                    int c= Integer.parseInt(parts[2]);
//                    DateAndTime.cheatDays(c);
//                    int e= Integer.parseInt(parts[4]);
//                    int b= Integer.parseInt(parts[5]);
//                    //PlantController.showPlant(e, b);
//                    break;
            case "bond":
                String name = parts[3];
                Animal a = player.getBroughtAnimal().get(name);
                if (a == null) {
                    System.out.println(name + " not found!");
                    break;
                }

                String action = parts[4];

                switch (action) {
                    case "pet":
                        a.pet();
                        break;
                    case "milk":
                    case "shear":
                        a.milkOrShear();
                        break;
                    case "grass":
                        a.feed();
                        break;
                    case "notFed":
                        a.applyHungerPenalty();
                        break;
                    case "outsideNight":
                        a.applyColdNightPenalty();
                        break;
                    case "notPetted":
                        a.applyNeglectPenalty();
                        break;
                    default:
                        System.out.println("Unknown bond action: " + action);
                }
                break;
            case "eat":
                if (parts.length < 5 || !parts[2].equals("-l")) {
                    System.out.println("Usage: eat AnimalName -l <x> <y>");
                    break;
                }

                String animalName = parts[1];
                int p = Integer.parseInt(parts[3]);
                int o = Integer.parseInt(parts[4]);

                Animal animal = player.getBroughtAnimal().get(animalName);
                if (animal == null) {
                    System.out.println("No animal named '" + animalName + "' found.");
                    break;
                }
                WorldMap worldMap1 = GameManager.getCurrentGame().getWorldMap();

                worldMap1.replaceTileTypeIfMatch(p, o, TileType.TREE, TileType.EMPTY);

                System.out.printf("%s is eating at location (%d, %d)%n", animalName, p, o);
                animal.feed();
                break;
            case "uncollect":
                AnimallController.showCollectedProducts(player);
                break;
            case "fishing":
                if (parts.length >= 3 && parts[1].equals("-p")) {
                    String fishpoleName = parts[2];
                    FishingPoleType type = FishingPoleType.fromName(fishpoleName);

                    if (type != null) {
                        FishingPole pole = new FishingPole(type);
                        player.setEquippedItem(pole);
                        System.out.println("Equipped fishing pole: " + fishpoleName);
                    } else {
                        System.out.println("No such fishing pole exists!");
                        break;
                    }

                    Season season = DateAndTime.getCurrentSeason();
                    WeatherType weather = Weather.getToday();
                    FishCreator.catchFishes(season, player.getFishSkill(), 4, weather);
                    player.setFishSkill(player.getFishSkill() + 5);

                    System.out.println("Fishing done. FishSkill updated.");
                } else {
                    System.out.println("Usage: fishing -p <FishingPoleName>");
                }
                break;

            case "add":
                String itemNamee = parts[1].toLowerCase();
                ItemFactory.createItem(itemNamee, player.getInventory());
                System.out.println("Added " + itemNamee);
                break;

            case "talk":

                if (parts[1].equals("-u")) {

                    String username = parts[2];

                    String message = parts[4].trim();

                    System.out.println(FriendshipController.talk(player, username, message));

                } else if (parts[1].equals("history")) {

                    String username = parts[3];

                    System.out.println(FriendshipController.showTalkHistory(player, username));

                }

                break;

            case "gift":

                if (parts[1].equals("-u")) {

                    String username = parts[2];

                    String item = parts[4].trim();

                    int amt = Integer.parseInt(parts[6]);

                    System.out.println(FriendshipController.sendGift(player, username, item, amt));



                } else if (parts[1].equals("list")) {



                    System.out.println(FriendshipController.listGifts(player));

                } else if (parts[1].equals("rate")) {

                    int giftNumber = Integer.parseInt(parts[3].trim());

                    int rate = Integer.parseInt(parts[5].trim());

                    System.out.println(FriendshipController.rateGift(player, giftNumber, rate));

                } else if (parts[1].equals("history")) {

                    String username = parts[3];

                    System.out.println(FriendshipController.giftHistory(player, username));

                }

                break;

            case "friendships":

                System.out.println(FriendshipController.showFriendships(player));

                break;

            case "hug":

                String targetUsername = parts[2];

                System.out.println(FriendshipController.hugPlayer(player, targetUsername));

                break;

            case "flower":

                String receiverUsername = parts[2];

                System.out.println(FriendshipController.sendFlower(player, receiverUsername));

                break;

            case "ask":

                String username = parts[3];

                String ring = parts[5].trim();

                System.out.println(FriendshipController.askMarriage(player, username, ring));

                break;

            case "respond":

                String isAccepted = parts[1];

                boolean accept = isAccepted.contains("accept");

                boolean reject = isAccepted.contains("reject");

                String maleUsername = parts[3].trim();

                System.out.println(FriendshipController.respondMarriage(player, maleUsername, accept, reject));

                break;

            case "cooking":

                if (parts[1].equals("refrigerator")) {

                    String actionn = parts[2];

                    String itemName = parts[3];

                    System.out.println(CookController.handleRefrigeratorCommand(player,actionn,itemName));

                }else if(parts[1].equals("show")){

                    System.out.println(CookController.showLearnedRecipes(player));

                }else if(parts[1].equals("prepare")){

                    String recipeName = parts[2];

                    System.out.println(CookController.prepareFood(player,recipeName));

                }

            case "eatforplayer":

                String recipeName = parts[1];

                System.out.println(CookController.eat(player,recipeName));

                break;



            default:

                Result.failure("Invalid command");

        }
    }

    public static Shop getCurrentShopForPlayer(Player player) {
        Game game = GameManager.getCurrentGame();
        WorldMap worldMap = game.getWorldMap();
        Position pos = player.getPosition();
        int x = pos.getCol();
        int y = pos.getRow();
        String shopName = worldMap.getShopNameAt(x, y);
        if (shopName == null) {
            return null;
        }

        ShopType playerShopType = null;
        for (ShopType type : ShopType.values()) {
            if (type.getOwnerName().equalsIgnoreCase(shopName)) {
                playerShopType = type;
                break;
            }
        }

        if (playerShopType == null) {
            System.out.println("You are not inside a valid shop.");
            return null;
        }

        return getShopManager().getShop(playerShopType);
    }


//    public static void handleArtisanCommand(String command) {
//        if (command.startsWith("artisan use")) {
//            Matcher matcher = MenuCommands.ARTISAN_USE.getPattern().matcher(command);
//            if (matcher.matches()) {
//                String artisanName = matcher.group("artisan");
//                String itemsStr = matcher.group("items").trim();
//                List<String> items = Arrays.asList(itemsStr.split(" "));
//                String result = ArtisanRecipes.useArtisan(GameManager.getCurrentGame().getCurrentPlayerForPlay(), artisanName, items);
//                System.out.println(result);
//            } else {
//                System.out.println("❌ فرمت نادرست دستور artisan use");
//            }
//        } else if (command.startsWith("artisan get")) {
//            Matcher matcher = MenuCommands.ARTISAN_GET.getPattern().matcher(command);
//            if (matcher.matches()) {
//                String artisanName = matcher.group("artisan");
//                String result = ArtisanRecipes.getProduct(GameManager.getCurrentGame().getCurrentPlayerForPlay(), artisanName);
//                System.out.println(result);
//            } else {
//                System.out.println("❌ فرمت نادرست دستور artisan get");
//            }
//        }
//    }


    public static void handleMeetNPC(String command) {
        Matcher matcher = MenuCommands.MEET_NPC.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("Invalid command format.");
            return;
        }

        String npcName = matcher.group("npcName");
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();

        Position playerPos = player.getPosition();
        WorldMap map = game.getWorldMap();
        int level = player.getFriendshipLevel(npcName);
        List<Position> adj = map.getAdjacentPositions(playerPos);
        for (Quest quest : QuestManager.getQuestsFor(npcName)) {
            if (quest.canBeActivated(level, DateAndTime.getDay())) {
                player.addActiveQuest(quest);
                System.out.println("🎯 New quest activated: " + quest.getDescription());
            }
        }

        //   System.out.println("has npc " + npcName);
        NPC targetNPC = null;
        for (NPC npc : map.getNpcs()) {
            //   System.out.println("npc " + npc.getName());
            if (npc.getName().equalsIgnoreCase(npcName)) {
                Position npcHome = map.getNPCHomePosition(npcName);
                // System.out.println("npc home " + npcHome);
                if (adj.contains(npcHome)) {
                    targetNPC = npc;
                    break;
                }
            }
        }

        if (targetNPC == null) {
            System.out.println("You are not close enough to this NPC.");
            return;
        }

        Season currentSeason = DateAndTime.getCurrentSeason();
        WeatherType weather = WeatherType.SUNNY;

        String dialogue = targetNPC.getRandomDialogue(currentSeason, weather);
        System.out.println(targetNPC.getName() + " says: " + dialogue);

        if (!player.hasMetToday(targetNPC.getName())) {
            player.addFriendshipPoints(targetNPC.getName(), 20);
            player.markMetToday(targetNPC.getName());
        }
    }

    public static void handleGiftCommand(String command) {
        Matcher matcher = Pattern.compile("gift NPC (?<name>\\w+) -i (?<item>.+)", Pattern.CASE_INSENSITIVE).matcher(command);
        if (!matcher.matches()) {
            System.out.println("Invalid command format.");
            return;
        }

        String npcName = matcher.group("name");
        String itemName = matcher.group("item").toLowerCase();

        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        WorldMap map = game.getWorldMap();

        // موقعیت NPC
        Position npcPos = map.getNPCHomePosition(npcName);
        if (npcPos == null) {
            System.out.println("No such NPC found.");
            return;
        }


        if (!map.getAdjacentPositions(player.getPosition()).contains(npcPos)) {
            System.out.println("You are not close enough to the NPC.");
            return;
        }

        if (!player.getInventory().hasItem(itemName)) {
            System.out.println("You don't have this item.");
            return;
        }

//        if (player.getInventory().getItems().get(itemName).i) {
//            System.out.println("You can't gift tools.");
//            return;
//        }

        NPC targetNPC = map.getNpcs().stream()
            .filter(n -> n.getName().equalsIgnoreCase(npcName))
            .findFirst().orElse(null);

        if (targetNPC == null) {
            System.out.println("NPC not found.");
            return;
        }

        if (player.hasGiftedToday(npcName)) {
            System.out.println("You already gifted this NPC today.");
            return;
        }

        int points = targetNPC.likes(itemName) ? 200 : 50;
        player.addFriendshipPoints(npcName, points);
        player.markGiftedToday(npcName);
        player.getInventory().removeItem(itemName, 1);

        System.out.println("Gifted " + itemName + " to " + npcName + ". Friendship +" + points);
    }

    public static void handleList() {
        Game game = GameManager.getCurrentGame();
        WorldMap map = game.getWorldMap();
        Player player = game.getCurrentPlayerForPlay();

        System.out.println("📋 Friendship Levels:");
        for (NPC npc : map.getNpcs()) {
            String name = npc.getName();
            int points = player.getFriendshipPoints(name);
            int level = player.getFriendshipLevel(name);
            System.out.printf("- %s: %d points (Level %d)%n", name, points, level);
        }
    }

    public static void handleQuestList() {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        List<Quest> quests = player.getActiveQuests();
        if (quests.isEmpty()) {
            System.out.println("No active quests.");
        } else {
            for (int i = 0; i < quests.size(); i++) {
                System.out.println((i + 1) + ". " + quests.get(i));
            }
        }
    }

    //    public static void handleQuestFinish(Player player, int index) {
//        List<Quest> quests = player.getActiveQuests();
//        if (index < 1 || index > quests.size()) {
//            System.out.println("Invalid quest index.");
//            return;
//        }
//
//        Quest quest = quests.get(index - 1);
//        if (!InventoryUtils.hasItems(player.getInventory(), quest.getRequiredItems())) {
//            System.out.println("You don't have the required items.");
//            return;
//        }
//
//        if (!WorldMap.isNpcNearby(player.getPosition(), quest.getNpcName())) {
//            System.out.println("You must be near " + quest.getNpcName() + " to finish this quest.");
//            return;
//        }
//
//        InventoryUtils.removeItems(player.getInventory(), quest.getRequiredItems());
//        player.completeQuest(quest);
//        RewardSystem.give(player, quest.getReward());
//        System.out.println("✅ Quest completed!");
//    }
    public static void handleQuestFinish(String command) {
        Matcher matcher = MenuCommands.FINISH_QUEST.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        int index = Integer.parseInt(matcher.group("index"));
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        List<Quest> quests = player.getActiveQuests();
        if (index < 1 || index > quests.size()) {
            System.out.println("Invalid quest index.");
            return;
        }

        Quest quest = quests.get(index - 1);
        if (!InventoryUtils.hasItems(player.getInventory(), quest.getRequiredItems())) {
            System.out.println("You don't have the required items.");
            return;
        }

        if (!WorldMap.isPlayerNearNpc(player, quest.getNpcName())) {
            System.out.println("You must be near " + quest.getNpcName() + " to finish this quest.");
            return;
        }

        InventoryUtils.removeItems(player.getInventory(), quest.getRequiredItems());
        player.completeQuest(quest);
        RewardSystem.give(player, quest.getReward());
        System.out.println("✅ Quest completed!");
    }
}
