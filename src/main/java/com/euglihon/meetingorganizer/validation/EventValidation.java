package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EventValidation {

    private static boolean validateTitle(TextField title, Label response) {
        boolean isTitleValid = ValidationRules.isTitle(title.getText());
        ViewHelpers.validateField(title, isTitleValid, "Invalid title", response);
        return isTitleValid;
    }

    private static boolean validateDate(DatePicker date, Label response) {
        boolean isDateValid = ValidationRules.isValidDate(date);
        ViewHelpers.validateField(date, isDateValid, "Invalid date", response);
        return isDateValid;
    }

    private static boolean validateCategory(ComboBox<Category> category, Label response) {
        boolean isCategoryValid = ValidationRules.isValidComboBox(category);
        ViewHelpers.validateField(category, isCategoryValid, "Invalid category", response);
        return isCategoryValid;
    }

    public static boolean validateForm(TextField title, DatePicker date, ComboBox<Category> categoryItem, Label response) {
        ViewHelpers.clearResponseMessage(response);
        return validateTitle(title, response) && validateCategory(categoryItem, response) && validateDate(date, response);
    }

    public static boolean validateForm(TextField title, DatePicker date, Label response) {
        ViewHelpers.clearResponseMessage(response);
        return validateTitle(title, response) && validateDate(date, response);
    }

    public static boolean validateForm(DatePicker date, Label response) {
        ViewHelpers.clearResponseMessage(response);
        return validateDate(date, response);
    }
}
