package io.github.some_example_name.model.Lobby;

import io.github.some_example_name.model.database.DatabaseLobbyManager;

import java.util.*;
import java.util.stream.Collectors;
public class LobbyManager {
    private static final Map<String, LobbyInfo> lobbies = new HashMap<>();

    public static void addLobby(LobbyInfo lobby) {
        lobbies.put(lobby.getId(), lobby);
    }

    public static void removeLobby(String id) {
        lobbies.remove(id);
        DatabaseLobbyManager.deleteLobby(id);
    }

    public static LobbyInfo getLobbyById(String id) {
        return lobbies.get(id);
    }

    public static List<LobbyInfo> getVisibleLobbies() {
        List<LobbyInfo> result = new ArrayList<>();
        for (LobbyInfo lobby : lobbies.values()) {
            if (lobby.isVisible()) result.add(lobby);
        }
        return result;
    }

    public static LobbyInfo joinById(String id, String password, String username) {
        LobbyInfo lobby = getLobbyById(id);
        if (lobby == null) return null;
        if (lobby.isPrivate() && !lobby.checkPassword(password)) return null;
        lobby.addPlayer(username);
        return lobby;
    }

    public static void cleanEmptyLobbies() {
        long now = System.currentTimeMillis();
        List<String> toRemove = new ArrayList<>();

        for (LobbyInfo lobby : lobbies.values()) {
            boolean shouldRemove = false;

            if (lobby.getPlayers().isEmpty()) {
                shouldRemove = true;
            } else if (!lobby.isStarted() && now - lobby.getLastJoinTime() > 5 * 60 * 1000) {
                shouldRemove = true;
            }

            if (shouldRemove) {
                toRemove.add(lobby.getId());
            }
        }

        for (String id : toRemove) {
            lobbies.remove(id);
            DatabaseLobbyManager.deleteLobby(id); // ← حذف از دیتابیس
        }
    }


    public static LobbyInfo createLobby(String name, boolean isPrivate, boolean isVisible, String password, String creatorUsername) {
        LobbyInfo lobby = new LobbyInfo(name, isPrivate, isVisible, password, creatorUsername);
        addLobby(lobby);
        return lobby;
    }

    public static Collection<LobbyInfo> getAllLobbies() {
        return lobbies.values();
    }
    public static boolean lobbyExists(String lobbyId) {
        return lobbies.containsKey(lobbyId);
    }

    public static void saveAllLobbiesToDatabase() {
        for (LobbyInfo lobby : lobbies.values()) {
            DatabaseLobbyManager.saveLobby(lobby);
        }
    }

    public static void loadAllLobbiesFromDatabase() {
        List<LobbyInfo> loaded = DatabaseLobbyManager.loadAllVisibleLobbies();
        for (LobbyInfo lobby : loaded) {
            lobbies.put(lobby.getId(), lobby);
        }
    }


}
