package com.euglihon.meetingorganizer.validation;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRules {

    public static boolean isName(String str) {
        int strLength = str.length();
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher matcher = pattern.matcher(str);
        return strLength > 1 && strLength < 20 && matcher.matches();
    }

    public static boolean isTitle(String str) {
        int strLength = str.length();
        return strLength > 1 && strLength < 100;
    }

    public static boolean isValidPhoneNumber(String str) {
        Pattern pattern = Pattern.compile("^\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isValidComboBox(ComboBox comboBox) {
        return comboBox.getSelectionModel().getSelectedItem() != null;
    }

    public static boolean isValidDate(DatePicker datePicker) {
        return datePicker.getValue() != null;
    }
}
