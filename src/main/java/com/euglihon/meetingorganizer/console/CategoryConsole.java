package com.euglihon.meetingorganizer.console;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.enums.Color;
import com.euglihon.meetingorganizer.service.ICategoryService;

import java.util.List;
import java.util.Scanner;

/**
 * CategoryConsole class responsible for interacting with the user
 * to manage categories in the console interface.
 */
public class CategoryConsole {

    private final ICategoryService categoryService;

    /**
     * Constructs a CategoryConsole with the specified category service.
     *
     * @param categoryService the service for managing categories
     */
    public CategoryConsole(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Displays all categories with their IDs, names, and colors.
     * If no categories are found, displays a message indicating so.
     */
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

    /**
     * Deletes a category based on the ID entered by the user.
     * Prompts the user to input the ID of the category to delete.
     */
    public void deleteCategory() {
        System.out.print("Enter category ID to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        categoryService.deleteCategoryById(id);
    }

    /**
     * Creates a new category based on user input.
     * Prompts the user to input the category name and choose a color.
     */
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
