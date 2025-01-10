package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EventValidation {

    public static boolean validateFields(TextField titleTextField, DatePicker datePicker, ComboBox comboBox, Label responseLabel) {
        ViewHelpers.ClearResponseMessage(responseLabel);
        boolean isValid = true;

        isValid &= ValidationRules.validateField(
                titleTextField,
                ValidationRules.isTitle(titleTextField.getText()),
                "Invalid title",
                responseLabel
        );

        isValid &= ValidationRules.validateField(
                datePicker,
                ValidationRules.isValidDate(datePicker),
                "Invalid date",
                responseLabel
        );

        isValid &= ValidationRules.validateField(
                comboBox,
                ValidationRules.isValidComboBox(comboBox),
                "Invalid category",
                responseLabel
        );

        return isValid;
    }
}
