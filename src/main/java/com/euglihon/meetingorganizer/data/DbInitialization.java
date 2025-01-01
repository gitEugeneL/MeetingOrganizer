package com.euglihon.meetingorganizer.data;

import com.euglihon.meetingorganizer.repository.category.ICategoryRepository;

import java.sql.SQLException;

public class DbInitialization {

    private final ICategoryRepository categoryRepository;
    public DbInitialization(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void init() throws SQLException {
        categoryRepository.createTable();
    }
}
