package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Category;

import java.util.List;

/**
 * This interface defines the methods for managing category data in the repository.
 */
public interface ICategoryRepository {

    /**
     * Create the categories table in the database.
     */
    void createTable();

    /**
     * Insert a new category into the database.
     *
     * @param category the category to insert.
     */
    void insert(Category category);

    /**
     * Delete a category by its ID.
     *
     * @param categoryId the ID of the category to delete.
     */
    void deleteById(int categoryId);

    /**
     * Find all categories in the database.
     *
     * @return a list of all categories.
     */
    List<Category> findAll();

    /**
     * Find a category by its name.
     *
     * @param name the name of the category.
     * @return the category with the specified name, or null if not found.
     */
    Category findByName(String name);
}
