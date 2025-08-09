package io.github.some_example_name.model.game;

import java.io.*;
import java.util.*;

public class GameManager {
    private static final Map<String, Game> userToGame = new HashMap<>();
    private static final Set<String> activePlayers = loadActivePlayers();
    private static Game currentGame;
    private static final String FILE_PATH = "active_players.txt";


    public static boolean isUserInAnyGame(String username) {
        return activePlayers.contains(username);
    }

    public static void createGame(List<String> usernames, String creatorUsername) {
        Game game = new Game(usernames, creatorUsername);
        for (String username : usernames) {
            userToGame.put(username, game);
        }
        currentGame = game;
    }

    public static void saveActivePlayers(Set<String> usernames) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String username : usernames) {
                writer.println(username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> loadActivePlayers() {
        Set<String> usernames = new HashSet<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return usernames;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                usernames.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usernames;
    }

    public static void addActivePlayer(String username) {
        activePlayers.add(username);
        saveActivePlayers(activePlayers);
    }

    public static void removePlayersFromActiveGame(List<String> usernames) {
        activePlayers.removeAll(usernames);
        saveActivePlayers(activePlayers);
    }


    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game game) {
        currentGame = game;
    }

}
