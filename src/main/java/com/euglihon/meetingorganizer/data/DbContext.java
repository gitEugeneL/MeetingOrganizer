package com.euglihon.meetingorganizer.data;

import java.sql.*;
import java.util.logging.Logger;

public class DbContext {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private Connection connection;

    public void getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:sqlite:organizer.db");
            }
        } catch (SQLException exception) {
            logger.info(exception.toString());
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
