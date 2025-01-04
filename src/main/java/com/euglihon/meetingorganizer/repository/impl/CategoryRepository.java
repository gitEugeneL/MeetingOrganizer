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

public final class CategoryRepository implements ICategoryRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final DbContext dbContext;
    public CategoryRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    private Category mapToCategory(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                Color.valueOf(resultSet.getString("color"))
        );
    }

    @Override
    public void createTable() {
        dbContext.getConnection();
        String query = """
                    CREATE TABLE IF NOT EXISTS Categories (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        color VARCHAR(20) NOT NULL CHECK (color IN ('RED', 'YELLOW', 'GREEN', 'BLUE')));
                """;
        try(PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.execute();
        } catch (SQLException ignored) {
            logger.info("Failed to create Categories table");
        }
    }

    @Override
    public void insert(Category category) {
        // todo
    }

    @Override
    public void deleteById(int categoryId) {
        // todo
    }

    @Override
    public List<Category> findAll() {
        dbContext.getConnection();
        String query = "SELECT id, name, color FROM Categories";
        List<Category> categories = new ArrayList<>();

        try(PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                categories.add(this.mapToCategory(resultSet));
            }
        } catch (SQLException ignored) {
            logger.info("Failed to find all Categories");
        }
        return categories;
    }
}
