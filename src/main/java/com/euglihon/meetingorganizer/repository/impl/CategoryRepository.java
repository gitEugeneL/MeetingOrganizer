package com.euglihon.meetingorganizer.repository.impl;

import com.euglihon.meetingorganizer.data.DbContext;
import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.enums.Color;
import com.euglihon.meetingorganizer.repository.ICategoryRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is an implementation of the ICategoryRepository interface.
 * It provides methods for managing categories in the database.
 */
public final class CategoryRepository implements ICategoryRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final DbContext dbContext;

    /**
     * Constructor that initializes the CategoryRepository with a DbContext.
     *
     * @param dbContext The database context for database operations.
     */
    public CategoryRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Maps a ResultSet row to a Category object.
     *
     * @param resultSet The ResultSet to map from.
     * @return A Category object.
     * @throws SQLException If there is an error accessing the ResultSet.
     */
    private Category mapToCategory(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                Color.valueOf(resultSet.getString("color"))
        );
    }

    /**
     * Creates the Categories table in the database if it does not exist.
     */
    @Override
    public void createTable() {
        dbContext.getConnection();
        String query = """
                    CREATE TABLE IF NOT EXISTS Categories (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        color VARCHAR(20) NOT NULL CHECK (color IN ('RED', 'YELLOW', 'GREEN', 'BLUE')));
                """;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.execute();
        } catch (SQLException ignored) {
            logger.severe("Failed to create Categories table");
        }
    }

    /**
     * Inserts a new category into the Categories table.
     *
     * @param category The category to insert.
     */
    @Override
    public void insert(Category category) {
        dbContext.getConnection();
        String query = "INSERT INTO Categories (name, color) VALUES (?, ?)";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getColor().toString());
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to insert Category");
        }
    }

    /**
     * Deletes a category by its ID from the Categories table.
     *
     * @param categoryId The ID of the category to delete.
     */
    @Override
    public void deleteById(int categoryId) {
        dbContext.getConnection();
        String query = "DELETE FROM Categories WHERE id = ?";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to delete Category");
        }
    }

    /**
     * Finds all categories in the Categories table.
     *
     * @return A list of all categories.
     */
    @Override
    public List<Category> findAll() {
        dbContext.getConnection();
        String query = "SELECT id, name, color FROM Categories";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Map each result row to a Category and add to the list
                categories.add(this.mapToCategory(resultSet));
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to find all Categories");
        }
        // Return the list of categories
        return categories;
    }

    /**
     * Finds a category by its name.
     *
     * @param name The name of the category to find.
     * @return The found Category, or null if not found.
     */
    @Override
    public Category findByName(String name) {
        dbContext.getConnection();
        String query = "SELECT id, name, color FROM Categories WHERE name = ?";
        Category category = null;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Map the result to a Category if found
                category = this.mapToCategory(resultSet);
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to find Category by name");
        }
        // Return the found category or null if not found
        return category;
    }
}
