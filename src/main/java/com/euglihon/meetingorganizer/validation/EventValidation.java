package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EventValidation {

    private static boolean validateFields(TextField title, DatePicker date,
                                         ComboBox<Category> category, Label response) {

        ViewHelpers.clearResponseMessage(response);

        boolean isTitleValid = ValidationRules.isTitle(title.getText());
        boolean isDateValid = ValidationRules.isValidDate(date);
        boolean isCategoryValid = true;

        ViewHelpers.validateField(title, isTitleValid, "Invalid title", response);
        ViewHelpers.validateField(date,isDateValid, "Invalid date", response);

        if (category != null) {
            isCategoryValid = ValidationRules.isValidComboBox(category);
            ViewHelpers.validateField(category, isCategoryValid, "Invalid category", response);
        }
        return isTitleValid && isDateValid && isCategoryValid;
    }

    public static boolean validateForm(TextField title, DatePicker date, ComboBox<Category> categoryItem, Label response) {
        return validateFields(title, date, categoryItem, response);
    }

    public static boolean validateForm(TextField title, DatePicker date, Label response) {
        return validateFields(title, date, null, response);
    }
}
