package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ContactValidation {
    public static boolean validateFields(
            TextField firstNameTextField, TextField lastNameTextField, TextField phoneTextField, Label responseLabel) {
        ViewHelpers.ClearResponseMessage(responseLabel);
        boolean isValid = true;
        if (!ValidationRules.isNameOrSurname(firstNameTextField.getText())) {
            ViewHelpers.CreateResponseMessage(responseLabel, "Invalid first name");
            ViewHelpers.ChangeTextFieldBorderColor(firstNameTextField, false);
            isValid = false;
        }
        if (!ValidationRules.isNameOrSurname(lastNameTextField.getText())) {
            ViewHelpers.CreateResponseMessage(responseLabel, "Invalid last name");
            ViewHelpers.ChangeTextFieldBorderColor(lastNameTextField, false);
            isValid = false;
        }
        if (!ValidationRules.isValidPhoneNumber(phoneTextField.getText())) {
            ViewHelpers.CreateResponseMessage(responseLabel, "Invalid phone number format");
            ViewHelpers.ChangeTextFieldBorderColor(phoneTextField, false);
            isValid = false;
        }
        return isValid;
    }
}



