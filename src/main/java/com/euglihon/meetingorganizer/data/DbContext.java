package com.euglihon.meetingorganizer.data;

import java.sql.*;
import java.util.logging.Logger;

public class DbContext {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private Connection connection;

    public void getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:sqlite:organizer.db");
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
