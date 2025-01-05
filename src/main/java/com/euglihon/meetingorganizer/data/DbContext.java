package com.euglihon.meetingorganizer.data;

import java.sql.*;
import java.util.logging.Logger;

/**
 * DbContext class manages the database connection and provides utility methods
 * to obtain prepared statements for executing SQL queries.
 */
public class DbContext {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private Connection connection;

    /**
     * Establishes a connection to the SQLite database.
     * If the connection is already established and not closed, it reuses the existing connection.
     */
    public void getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Establish a new connection to the SQLite database
                connection = DriverManager.getConnection("jdbc:sqlite:organizer.db");
            }
        } catch (SQLException e) {
            // Log the exception details if connection fails
            logger.severe("Failed to establish database connection: " + e.toString());
        }
    }

    /**
     * Closes the current database connection if it is open.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                // Close the connection to release resources
                connection.close();
            }
        } catch (SQLException e) {
            // Log the exception details if closing the connection fails
            logger.severe("Failed to close database connection: " + e.toString());
        }
    }

    /**
     * Prepares an SQL statement for execution.
     *
     * @param query The SQL query to be prepared.
     * @return A PreparedStatement object to execute the query.
     * @throws SQLException If an error occurs while preparing the statement.
     */
    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is not established. Call getConnection() first.");
        }
        // Prepare and return the SQL statement
        return connection.prepareStatement(query);
    }
}
