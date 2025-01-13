package com.euglihon.meetingorganizer.console;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.enums.Color;
import com.euglihon.meetingorganizer.service.ICategoryService;

import java.util.List;
import java.util.Scanner;

public class CategoryConsole {

    private final ICategoryService categoryService;
    public CategoryConsole(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void showAllCategories() {
        List<Category> categories = this.categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            System.out.println("\nCategories:");
            for (Category category : categories) {
                System.out.println("Id: " + category.getId()
                        + ", Name: " + category.getName()
                        + ", Color: " + category.getColor().toString());
            }
        }
    }

    public void deleteCategory() {
        System.out.print("Enter category ID to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        categoryService.deleteCategoryById(id);
    }

    public void createCategory() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter category name: ");
        String name = scanner.nextLine();

        System.out.println("Choose category color:");
        for (Color color : Color.values()) {
            System.out.println(color.ordinal() + ": " + color);
        }

        int colorChoice = Integer.parseInt(scanner.nextLine());
        Color color = Color.values()[colorChoice];

        Category category = new Category();
        category.setName(name);
        category.setColor(color);
        this.categoryService.addCategory(category);
    }
}
