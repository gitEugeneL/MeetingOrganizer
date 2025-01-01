package com.euglihon.meetingorganizer.repository.category;

import com.euglihon.meetingorganizer.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryRepository {

    void createTable() throws SQLException;

    void insert(Category category);

    void deleteById(int categoryId);

    List<Category> getAll();
}
