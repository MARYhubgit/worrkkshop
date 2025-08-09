package io.github.some_example_name.model.database;

import io.github.some_example_name.model.Lobby.LobbyInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseLobbyManager {
    public static void saveLobby(LobbyInfo lobby) {
        String sql = "INSERT INTO lobbies(id, name, is_private, is_visible, password, admin_username, last_join_time, started) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, lobby.getId());
            stmt.setString(2, lobby.getName());
            stmt.setInt(3, lobby.isPrivate() ? 1 : 0);
            stmt.setInt(4, lobby.isVisible() ? 1 : 0);
            stmt.setString(5, lobby.getPassword());
            stmt.setString(6, lobby.getAdminUsername());
            stmt.setLong(7, lobby.getLastJoinTime());
            stmt.setInt(8, lobby.isStarted() ? 1 : 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LobbyInfo loadLobby(String id) {
        String sql = "SELECT * FROM lobbies WHERE id = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LobbyInfo lobby = new LobbyInfo(
                    rs.getString("name"),
                    rs.getInt("is_private") == 1,
                    rs.getInt("is_visible") == 1,
                    rs.getString("password"),
                    rs.getString("admin_username")
                );
                lobby.updateJoinTime();
                if (rs.getInt("started") == 1)
                    lobby.startGame();
                return lobby;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<LobbyInfo> loadAllVisibleLobbies() {
        List<LobbyInfo> lobbies = new ArrayList<>();
        String sql = "SELECT * FROM lobbies WHERE is_visible = 1";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LobbyInfo lobby = new LobbyInfo(
                    rs.getString("name"),
                    rs.getInt("is_private") == 1,
                    true,
                    rs.getString("password"),
                    rs.getString("admin_username")
                );
                lobby.updateJoinTime();
                if (rs.getInt("started") == 1)
                    lobby.startGame();
                lobbies.add(lobby);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lobbies;
    }
    public static void deleteLobby(String id) {
        String sql = "DELETE FROM lobbies WHERE id = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean lobbyExists(String lobbyId) {
        String sql = "SELECT 1 FROM lobbies WHERE id = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, lobbyId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
