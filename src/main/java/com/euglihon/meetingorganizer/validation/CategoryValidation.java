package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.enums.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Provides validation methods for the Category form.
 * Ensures that the name and color fields are properly validated before submission.
 */
public class CategoryValidation {

    /**
     * Validates the name and color fields of the Category form.
     * Displays appropriate error messages if validation fails.
     *
     * @param name     The TextField containing the category name.
     * @param color    The ComboBox containing the category color.
     * @param response The Label used to display validation messages.
     * @return true if both fields are valid, false otherwise.
     */
    private static boolean validateFields(TextField name, ComboBox<Color> color, Label response) {
        // Clear any previous response messages
        ViewHelpers.clearResponseMessage(response);

        // Validate the name and color fields
        boolean isNameValid = ValidationRules.isValidName(name.getText());
        boolean isColorValid = ValidationRules.isValidComboBox(color);

        // Show validation messages for each field
        ViewHelpers.validateField(name, isNameValid, "Invalid category name", response);
        ViewHelpers.validateField(color, isColorValid, "Invalid color", response);

        // Return true only if both fields are valid
        return isNameValid && isColorValid;
    }

    /**
     * Public method to validate the entire Category form.
     * Calls the private validateFields method to perform the actual validation.
     *
     * @param name     The TextField containing the category name.
     * @param color    The ComboBox containing the category color.
     * @param response The Label used to display validation messages.
     * @return true if the form is valid, false otherwise.
     */
    public static boolean validateForm(TextField name, ComboBox<Color> color, Label response) {
        return validateFields(name, color, response);
    }
}
