package io.github.some_example_name.model.game;


import io.github.some_example_name.model.farm.FarmTemplate;
import io.github.some_example_name.model.market.MarketTemplate;
import io.github.some_example_name.model.village.VillageTemplate;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class GameSaveManager {
//    public static void saveGame(Game game) {
//        String name = String.join("_", game.getUsernames()).toLowerCase();
//        String path = "saved_games/" + name + ".json";
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(FarmTemplate.class, new FarmTemplateAdapter())
//                .registerTypeAdapter(MarketTemplate.class, new MarketTemplateAdapter())
//                .registerTypeAdapter(VillageTemplate.class, new VillageTemplateAdapter())
//                .registerTypeAdapter(Region.class, new RegionAdapter())
//                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
//                .enableComplexMapKeySerialization()
//                .setPrettyPrinting()
//                .create();
//
//        try {
//            File dir = new File("saved_games");
//            if (!dir.exists()) dir.mkdirs();
//
//            try (FileWriter writer = new FileWriter(path)) {
//                gson.toJson(game, writer);
//                System.out.println("✅ Game saved to: " + path);
//            }
//        } catch (IOException e) {
//            System.out.println("❌ Failed to save game: " + e.getMessage());
//        }
//    }
//    public static Game loadGame(String username) {
//        File dir = new File("saved_games");
//        if (!dir.exists() || !dir.isDirectory()) {
//            System.out.println("❌ No saved games found.");
//            return null;
//        }
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(FarmTemplate.class, new FarmTemplateAdapter())
//                .registerTypeAdapter(MarketTemplate.class, new MarketTemplateAdapter())
//                .registerTypeAdapter(VillageTemplate.class, new VillageTemplateAdapter())
//                .registerTypeAdapter(Region.class, new RegionAdapter())
//                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
//                .enableComplexMapKeySerialization()
//                .create();
//
//        for (File file : dir.listFiles()) {
//            if (file.getName().endsWith(".json")) {
//                try (FileReader reader = new FileReader(file)) {
//                    Game game = gson.fromJson(reader, Game.class);
//
//                    if (game.getUsernames().contains(username)) {
//                        game.setCurrentPlayer(game.getPlayers().get(game.getCurrentTurnIndex()));
//                        GameManager.setCurrentGame(game);
//                        System.out.println("✅ Loaded game from: " + file.getName());
//                        return game;
//                    }
//                } catch (IOException e) {
//                    System.out.println("⚠️ Couldn't read file " + file.getName());
//                }
//            }
//        }
//
//        System.out.println("❌ No saved game found for user: " + username);
//        return null;
//    }

}
