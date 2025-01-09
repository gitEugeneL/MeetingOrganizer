package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Category;

import java.util.List;

public interface ICategoryService {

    boolean addCategory(Category category);

    List<Category> getAllCategories();

    void deleteCategoryById(int id);
}
