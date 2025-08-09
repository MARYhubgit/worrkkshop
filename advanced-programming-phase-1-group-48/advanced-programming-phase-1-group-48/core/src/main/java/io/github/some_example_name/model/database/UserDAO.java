package io.github.some_example_name.model.database;

import java.sql.*;

public class UserDAO {

    public static boolean registerUser(String username, String password) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // برای امنیت واقعی باید هش بشه
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Registration failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean checkLogin(String username, String password) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Login check failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean usernameExists(String username) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT 1 FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Username check failed: " + e.getMessage());
            return false;
        }
    }
}
