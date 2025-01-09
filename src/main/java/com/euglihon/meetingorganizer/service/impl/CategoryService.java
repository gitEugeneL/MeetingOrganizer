package com.euglihon.meetingorganizer.service.impl;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.repository.ICategoryRepository;
import com.euglihon.meetingorganizer.service.ICategoryService;

import java.util.List;

public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean addCategory(Category category) {
        if (this.categoryRepository.findByName(category.getName()) != null) {
            return false;
        }
        this.categoryRepository.insert(category);
        return true;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(int id) {
        this.categoryRepository.deleteById(id);
    }
}
