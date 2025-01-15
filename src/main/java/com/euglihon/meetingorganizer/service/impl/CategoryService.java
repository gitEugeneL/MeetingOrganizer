package com.euglihon.meetingorganizer.service.impl;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.repository.ICategoryRepository;
import com.euglihon.meetingorganizer.service.ICategoryService;

import java.util.List;

/**
 * Implementation of the ICategoryService interface for managing categories.
 */
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    /**
     * Constructor to initialize the CategoryService with a repository.
     *
     * @param categoryRepository the repository used for category operations.
     */
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Add a new category if it does not already exist.
     *
     * @param category the category to add.
     * @return true if the category was added, false if it already exists.
     */
    @Override
    public boolean addCategory(Category category) {
        // Check if a category with the same name already exists
        if (this.categoryRepository.findByName(category.getName()) != null) {
            return false;
        }
        // Insert the new category into the repository
        this.categoryRepository.insert(category);
        return true;
    }

    /**
     * Get all categories.
     *
     * @return a list of all categories.
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Delete a category by its ID.
     *
     * @param id the ID of the category to delete.
     */
    @Override
    public void deleteCategoryById(int id) {
        this.categoryRepository.deleteById(id);
    }
}
