package io.github.some_example_name.model.Lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LobbyInfo {
    private final String name;
    private final String id;
    private final boolean isPrivate;
    private final boolean isVisible;
    private final String password;
    private final List<String> players = new ArrayList<>();
    private String adminUsername;
    private long lastJoinTime;
    private boolean started = false;

    public LobbyInfo(String name, boolean isPrivate, boolean isVisible, String password, String creatorUsername) {
        this.name = name;
        this.id = UUID.randomUUID().toString().substring(0, 6);
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.password = password;
        this.adminUsername = creatorUsername;
        players.add(creatorUsername);
        updateJoinTime();
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getId() { return id; }
    public boolean isPrivate() { return isPrivate; }
    public boolean isVisible() { return isVisible; }
    public List<String> getPlayers() { return players; }
    public String getAdminUsername() { return adminUsername; }
    public boolean isStarted() { return started; }

    public void addPlayer(String username) {
        if (!players.contains(username)) {
            players.add(username);
            updateJoinTime();
        }
    }

    public void removePlayer(String username) {
        players.remove(username);
        if (players.isEmpty()) return;

        // اگر ادمین خارج شد، نفر بعدی ادمین شود
        if (adminUsername.equals(username)) {
            adminUsername = players.get(0);
        }
    }

    public boolean checkPassword(String input) {
        return password.equals(input);
    }

    public void startGame() {
        started = true;
    }

    public void updateJoinTime() {
        lastJoinTime = System.currentTimeMillis();
    }

    public long getLastJoinTime() {
        return lastJoinTime;
    }
}
