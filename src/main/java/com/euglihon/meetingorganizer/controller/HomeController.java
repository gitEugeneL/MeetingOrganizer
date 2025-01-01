package com.euglihon.meetingorganizer.controller;

import com.euglihon.meetingorganizer.services.category.ICategoryService;

public class HomeController {

    private ICategoryService categoryService;
    public HomeController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
