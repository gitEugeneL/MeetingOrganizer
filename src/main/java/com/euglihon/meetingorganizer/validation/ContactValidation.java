package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ContactValidation {
    public static boolean validateFields(
            TextField firstNameTextField, TextField lastNameTextField, TextField phoneTextField, ComboBox comboBox, Label responseLabel) {
        ViewHelpers.ClearResponseMessage(responseLabel);
        boolean isValid = true;

        isValid &= ValidationRules.validateField(
                firstNameTextField,
                ValidationRules.isName(firstNameTextField.getText()),
                "Invalid first name",
                responseLabel);

        isValid &= ValidationRules.validateField(
                lastNameTextField,
                ValidationRules.isName(lastNameTextField.getText()),
                "Invalid last name",
                responseLabel);

        isValid &= ValidationRules.validateField(
                phoneTextField,
                ValidationRules.isValidPhoneNumber(phoneTextField.getText()),
                "Invalid phone number format",
                responseLabel);

        isValid &= ValidationRules.validateField(
                comboBox,
                ValidationRules.isValidComboBox(comboBox),
                "Invalid category",
                responseLabel);

        return isValid;
    }
}
