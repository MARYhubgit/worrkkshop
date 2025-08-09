package io.github.some_example_name.model.user;

import io.github.some_example_name.model.database.DatabaseManager;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserManager {
    private static final String SESSION_FILE = "last_session.txt";
    public static User currentUser = null;

    // افزودن کاربر جدید به دیتابیس
    public static void addUser(User user) {
        String sql = "INSERT INTO users(username, hashed_password, nickname, email, gender, security_question, hashed_answer) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getHashedPassword());
            stmt.setString(3, user.getNickname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getSecurityQuestion());
            stmt.setString(7, user.getHashedSecurityAnswer());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // جستجو کاربر بر اساس username
    public static User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return User.createFromDatabase(
                    rs.getString("username"),
                    rs.getString("hashed_password"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    rs.getString("gender"),
                    rs.getString("security_question"),
                    rs.getString("hashed_answer")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // بررسی اینکه کاربر با این username وجود دارد یا نه
    public static boolean userExists(String username) {
        return findByUsername(username) != null;
    }

    // به‌روزرسانی اطلاعات کاربر در دیتابیس
    public static void updateUser(User user) {
        String sql = "UPDATE users SET hashed_password = ?, nickname = ?, email = ?, gender = ?, security_question = ?, hashed_answer = ? " +
            "WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getHashedPassword());
            stmt.setString(2, user.getNickname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getGender());
            stmt.setString(5, user.getSecurityQuestion());
            stmt.setString(6, user.getHashedSecurityAnswer());
            stmt.setString(7, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // تغییر username کاربر
    public static void updateUsername(String oldUsername, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ذخیره نام کاربری جاری در فایل session
    public static void saveSession(User user) {
        try (PrintWriter pw = new PrintWriter(SESSION_FILE)) {
            pw.println(user.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // بارگذاری کاربر جاری از فایل session و بازیابی اطلاعات از دیتابیس
    public static User loadLastSession() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) return null;

        try (Scanner sc = new Scanner(file)) {
            if (sc.hasNextLine()) {
                String username = sc.nextLine();
                return findByUsername(username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // پاک کردن فایل session
    public static void clearSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) file.delete();
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
