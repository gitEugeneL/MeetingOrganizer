package com.euglihon.meetingorganizer.validation;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class that contains validation rules for various input fields.
 * These rules ensure that input data is valid before being processed or stored.
 */
public class ValidationRules {

    /**
     * Validates a name field.
     * The name must only contain letters and must be between 2 and 19 characters long.
     *
     * @param str The name string to validate.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidName(String str) {
        int strLength = str.length();
        // Pattern to allow only alphabetic characters
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher matcher = pattern.matcher(str);
        // Validate length between 2 and 19 and check if it matches the pattern
        return strLength > 1 && strLength < 20 && matcher.matches();
    }

    /**
     * Validates the title field.
     * The title must be between 2 and 99 characters long.
     *
     * @param str The title string to validate.
     * @return true if the title is valid, false otherwise.
     */
    public static boolean isValidTitle(String str) {
        int strLength = str.length();
        return strLength > 1 && strLength < 100;
    }

    /**
     * Validates a phone number field.
     * The phone number must follow the pattern: +XX-XXX-XXX-XXX (e.g. +12-345-678-901).
     *
     * @param str The phone number string to validate.
     * @return true if the phone number is valid, false otherwise.
     */
    public static boolean isValidPhoneNumber(String str) {
        Pattern pattern = Pattern.compile("^\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * Validates a ComboBox field.
     * The ComboBox must have a selected item.
     *
     * @param comboBox The ComboBox to validate.
     * @return true if the ComboBox has a valid selection, false otherwise.
     */
    public static boolean isValidComboBox(ComboBox comboBox) {
        return comboBox.getSelectionModel().getSelectedItem() != null;
    }

    /**
     * Validates a DatePicker field.
     * The DatePicker must have a selected date.
     *
     * @param datePicker The DatePicker to validate.
     * @return true if the DatePicker has a selected date, false otherwise.
     */
    public static boolean isValidDate(DatePicker datePicker) {
        return datePicker.getValue() != null;
    }
}
