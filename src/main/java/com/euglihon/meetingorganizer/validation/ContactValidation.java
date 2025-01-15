package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Provides validation methods for the Contact form.
 * Ensures that all required fields (first name, last name, phone, category) are validated properly.
 */
public class ContactValidation {

    /**
     * Validates the fields of the Contact form.
     * Checks if the first name, last name, phone number, and category are valid.
     *
     * @param firstName  The TextField containing the contact's first name.
     * @param lastName   The TextField containing the contact's last name.
     * @param phone      The TextField containing the contact's phone number.
     * @param category   The ComboBox containing the contact's category.
     * @param response   The Label used to display validation messages.
     * @return true if all fields are valid, false otherwise.
     */
    private static boolean validateFields(TextField firstName, TextField lastName,
                                          TextField phone, ComboBox<Category> category, Label response) {
        // Clear any previous response messages
        ViewHelpers.clearResponseMessage(response);

        // Validate individual fields
        boolean isFirstNameValid = ValidationRules.isValidName(firstName.getText());
        boolean isLastNameValid = ValidationRules.isValidName(lastName.getText());
        boolean isPhoneValid = ValidationRules.isValidPhoneNumber(phone.getText());
        boolean isCategoryValid = ValidationRules.isValidComboBox(category);

        // Display validation messages for each field
        ViewHelpers.validateField(firstName, isFirstNameValid, "Invalid first name", response);
        ViewHelpers.validateField(lastName, isLastNameValid, "Invalid last name", response);
        ViewHelpers.validateField(phone, isPhoneValid, "Invalid phone", response);
        ViewHelpers.validateField(category, isCategoryValid, "Invalid category", response);

        // Return true only if all fields are valid
        return isFirstNameValid && isLastNameValid && isPhoneValid && isCategoryValid;
    }

    /**
     * Public method to validate the entire Contact form.
     * Uses the validateFields method to perform the actual validation.
     *
     * @param firstName   The TextField containing the contact's first name.
     * @param lastName    The TextField containing the contact's last name.
     * @param phone       The TextField containing the contact's phone number.
     * @param categoryItem    The ComboBox containing the contact's category.
     * @param response    The Label used to display validation messages.
     * @return true if the form is valid, false otherwise.
     */
    public static boolean validateForm(TextField firstName, TextField lastName,
                                       TextField phone, ComboBox<Category> categoryItem, Label response) {

        return validateFields(firstName, lastName, phone, categoryItem, response);
    }
}
