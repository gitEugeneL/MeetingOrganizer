package com.euglihon.meetingorganizer.controller.common;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import javafx.scene.control.ComboBox;

import java.util.List;

public class Base {

    public static void setupCategoryComboBox(List<Category> categories, ComboBox<Category> categoryComboBox) {
        categoryComboBox.getItems().addAll(categories);
        categoryComboBox.setCellFactory(comboBox -> ViewHelpers.CategoryWithColorComboBox());
        categoryComboBox.setButtonCell(ViewHelpers.CategoryWithColorComboBox());
    }

    public static void setupCategoryItemComboBox(List<Category> categories, ComboBox<Category> categoryItemComboBox) {
        categoryItemComboBox.getItems().setAll(categories);
        if (!categories.isEmpty()) {
            categoryItemComboBox.setValue(categoryItemComboBox.getItems().get(0));
        }
        categoryItemComboBox.setCellFactory(c -> ViewHelpers.CategoryWithColorComboBox());
        categoryItemComboBox.setButtonCell(ViewHelpers.CategoryWithColorComboBox());
    }
}
