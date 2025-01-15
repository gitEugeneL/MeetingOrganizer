package com.euglihon.meetingorganizer.controller;

import com.euglihon.meetingorganizer.helpers.ListCellHelpers;
import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.enums.Color;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.validation.CategoryValidation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class CategoryController {

    private final ICategoryService categoryService;

    /**
     * Constructor to initialize CategoryController with category service.
     *
     * @param categoryService the service responsible for category operations
     */
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private List<Category> categories;

    @FXML
    private ComboBox<Color> colorComboBox;
    @FXML
    private ListView<Category> categoryListView;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label responseLabel;

    /**
     * Initializes the controller by loading categories and setting up UI components.
     */
    @FXML
    private void initialize() {
        // Load all categories from the service
        this.loadAllCategories();
        // Refresh the list view to show all categories
        this.refreshCategoryList(this.categories);
        // Set up the color combo box with color and names
        this.setupColorComboBox();
    }

    /**
     * Deletes the selected category from the list and updates the list view.
     */
    @FXML
    private void deleteCategory() {
        Category category = categoryListView.getSelectionModel().getSelectedItem();
        // If no category is selected, do nothing
        if (category == null) {
            return;
        }
        // Delete the category from the service
        this.categoryService.deleteCategoryById(category.getId());
        // Reload the categories and refresh the list view
        this.loadAllCategories();
        this.refreshCategoryList(this.categories);
    }

    /**
     * Adds a new category based on the form inputs.
     * Validates the inputs and if valid, creates and adds the category.
     * If the category already exists, displays an error message.
     */
    @FXML
    private void addCategory() {
        // Validate form inputs
        if (!CategoryValidation.validateForm(this.nameTextField, this.colorComboBox, this.responseLabel)) {
            return;
        }
        // Create a new category from the form data
        Category category = this.createCategoryFromForm();
        // Try to add the category and handle if it already exists
        if (!this.categoryService.addCategory(category)) {
            ViewHelpers.createResponseMessage(this.responseLabel, "Category already exists");
        } else {
            // Reset the form and refresh the list of categories
            this.resetForm();
            this.loadAllCategories();
            this.refreshCategoryList(this.categories);
        }
    }

    /**
     * Sets up the color combo box by adding all available colors.
     * Also, customizes how the colors are displayed in the combo box.
     */
    private void setupColorComboBox() {
        // Add all available colors to the combo box
        this.colorComboBox.getItems().addAll(Color.values());
        // Use custom cell factory to display colors with their name and color circle
        this.colorComboBox.setCellFactory(lv -> ListCellHelpers.colorNameWithColorCircle());
    }

    /**
     * Refreshes the category list view with the provided list of categories.
     * This method ensures the list view is updated with the latest data.
     */
    private void refreshCategoryList(List<Category> categories) {
        // Set all categories in the list view
        this.categoryListView.getItems().setAll(categories);
        // Customize how each category is displayed in the list view
        this.categoryListView.setCellFactory(lv -> ListCellHelpers.categoryWithColorComboBox());
    }

    /**
     * Loads all categories from the category service.
     * This method retrieves the list of categories from the service to be displayed in the UI.
     */
    private void loadAllCategories() {
        this.categories = this.categoryService.getAllCategories();
    }

    /**
     * Creates a new category object based on the form inputs.
     *
     * @return a new Category object
     */
    private Category createCategoryFromForm() {
        Category category = new Category();
        category.setName(this.nameTextField.getText());
        category.setColor(this.colorComboBox.getSelectionModel().getSelectedItem());
        return category;
    }

    /**
     * Resets the form by clearing the input fields and response label.
     */
    private void resetForm() {
        ViewHelpers.clearInputFields(this.nameTextField);
        ViewHelpers.clearResponseMessage(this.responseLabel);
    }
}
