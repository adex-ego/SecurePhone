package com.securephone.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    
    private static DatabaseManager instance;
    private Connection connection;
    
    private DatabaseManager() {
        connect();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/securephone";
            String user = "root";
            String password = "";
            
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("[DB] Connexion établie");
            
        } catch (Exception e) {
            System.err.println("[DB] Erreur connexion: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }
    
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}