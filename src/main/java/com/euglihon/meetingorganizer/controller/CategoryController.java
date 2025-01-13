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

    @FXML
    private void initialize() {
        this.loadAllCategories();
        this.refreshCategoryList(this.categories);
        this.setupColorComboBox();
    }

    @FXML
    private void deleteCategory() {
        Category category = categoryListView.getSelectionModel().getSelectedItem();
        if (category == null) {
            return;
        }
        this.categoryService.deleteCategoryById(category.getId());
        this.loadAllCategories();
        this.refreshCategoryList(this.categories);
    }

    @FXML
    private void addCategory() {
        if (!CategoryValidation.validateForm(this.nameTextField, this.colorComboBox, this.responseLabel)) {
            return;
        }
        Category category = this.createCategoryFromForm();
        if (!this.categoryService.addCategory(category)) {
            ViewHelpers.createResponseMessage(this.responseLabel, "Category already exists");
        } else {
            this.resetForm();
            this.loadAllCategories();
            this.refreshCategoryList(this.categories);
        }
    }

    private void setupColorComboBox() {
        this.colorComboBox.getItems().addAll(Color.values());
        this.colorComboBox.setCellFactory(lv -> ListCellHelpers.colorNameWithColorCircle());
    }

    private void refreshCategoryList(List<Category> categories) {
        this.categoryListView.getItems().setAll(categories);
        this.categoryListView.setCellFactory(lv -> ListCellHelpers.categoryWithColorComboBox());
    }

    private void loadAllCategories() {
        this.categories = this.categoryService.getAllCategories();
    }

    private Category createCategoryFromForm() {
        Category category = new Category();
        category.setName(this.nameTextField.getText());
        category.setColor(this.colorComboBox.getSelectionModel().getSelectedItem());
        return category;
    }

    private void resetForm() {
        ViewHelpers.clearInputFields(this.nameTextField);
        ViewHelpers.clearResponseMessage(this.responseLabel);
    }
}
