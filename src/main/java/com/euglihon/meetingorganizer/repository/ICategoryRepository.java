package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Category;

import java.util.List;

public interface ICategoryRepository {

    void createTable();

    void insert(Category category);

    void deleteById(int categoryId);

    List<Category> findAll();

    Category findByName(String name);
}
