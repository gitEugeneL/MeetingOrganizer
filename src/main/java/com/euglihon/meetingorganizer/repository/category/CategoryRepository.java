package com.euglihon.meetingorganizer.repository.category;

import com.euglihon.meetingorganizer.data.DbContext;
import com.euglihon.meetingorganizer.model.Category;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public final class CategoryRepository implements ICategoryRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final DbContext dbContext;
    public CategoryRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public void createTable() throws SQLException {
        dbContext.getConnection();
        try(var statement = dbContext.getPreparedStatement(CategoryQueries.CREATE_TABLE)) {
            statement.execute();
        } catch (SQLException ignored) {
            logger.info("Failed to create CATEGORY table");
        }
        dbContext.closeConnection();
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
    public List<Category> getAll() {
        // todo
        return List.of();
    }
}
