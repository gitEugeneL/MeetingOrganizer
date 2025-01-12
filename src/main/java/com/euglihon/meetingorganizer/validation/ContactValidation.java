package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ContactValidation {

    private static boolean validateFields(TextField firstName, TextField lastName,
                                          TextField phone, ComboBox<Category> category, Label response) {

        ViewHelpers.clearResponseMessage(response);

        boolean isFirstNameValid = ValidationRules.isName(firstName.getText());
        boolean isLastNameValid = ValidationRules.isName(lastName.getText());
        boolean isPhoneValid = ValidationRules.isValidPhoneNumber(phone.getText());
        boolean isCategoryValid = ValidationRules.isValidComboBox(category);

        ViewHelpers.validateField(firstName, isFirstNameValid,"Invalid first name", response);
        ViewHelpers.validateField(lastName, isLastNameValid, "Invalid last name", response);
        ViewHelpers.validateField(phone, isPhoneValid, "Invalid phone", response);
        ViewHelpers.validateField(category, isCategoryValid, "Invalid category", response);

        return isFirstNameValid && isLastNameValid && isPhoneValid && isCategoryValid;
    }

    public static boolean validateForm(TextField firstName, TextField lastName,
                                       TextField phone, ComboBox<Category> categoryItem, Label response) {

        return validateFields(firstName, lastName, phone, categoryItem, response);
    }
}
