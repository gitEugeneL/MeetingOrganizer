package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.enums.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CategoryValidation {

    private static boolean validateFields(TextField name, ComboBox<Color> color, Label response) {
        ViewHelpers.clearResponseMessage(response);

        boolean isNameValid = ValidationRules.isName(name.getText());
        boolean isColorValid = ValidationRules.isValidComboBox(color);

        ViewHelpers.validateField(name, isNameValid, "Invalid category name", response);
        ViewHelpers.validateField(color, isColorValid, "Invalid color", response);

        return isNameValid && isColorValid;
    }

    public static boolean validateForm(TextField name, ComboBox<Color> color, Label response) {
        return validateFields(name, color, response);
    }
}
