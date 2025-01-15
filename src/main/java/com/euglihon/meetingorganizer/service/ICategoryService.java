package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Category;

import java.util.List;

/**
 * Service interface for managing categories.
 */
public interface ICategoryService {

    /**
     * Adds a new category to the system.
     *
     * @param category The category to add.
     * @return true if the category was added successfully, false otherwise.
     */
    boolean addCategory(Category category);

    /**
     * Retrieves all categories from the system.
     *
     * @return A list of all categories.
     */
    List<Category> getAllCategories();

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     */
    void deleteCategoryById(int id);
}
