package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CategoryValidation {

    public static boolean validateFields(TextField nameTextField, ComboBox comboBox, Label responseLabel) {
        ViewHelpers.ClearResponseMessage(responseLabel);
        boolean isValid = true;

        isValid &= ValidationRules.validateField(
                nameTextField,
                ValidationRules.isName(nameTextField.getText()),
                "Invalid category name",
                responseLabel);

        isValid &= ValidationRules.validateField(
                comboBox,
                ValidationRules.isValidComboBox(comboBox),
                "Invalid color",
                responseLabel
        );

        return isValid;
    }
}
