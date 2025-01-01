package com.euglihon.meetingorganizer.services.category;

import com.euglihon.meetingorganizer.repository.category.ICategoryRepository;

public class CategoryService implements ICategoryService {

    private ICategoryRepository categoryRepository;
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
