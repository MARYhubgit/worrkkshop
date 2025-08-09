package io.github.some_example_name.model.database;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_PATH = "advanced-programming-phase-1-group-48/core/db/mydatabase.db";
    private static Connection connection;

    // اتصال به دیتابیس
    public static void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            System.out.println("✅ Connected to SQLite database.");
        }
    }

    // گرفتن Connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    // بستن اتصال
    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 SQLite connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
