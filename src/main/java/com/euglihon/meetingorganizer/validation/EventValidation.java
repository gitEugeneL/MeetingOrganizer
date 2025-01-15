package com.euglihon.meetingorganizer.validation;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Provides validation methods for the Event form.
 * Ensures that the title, date, and category fields are properly validated.
 */
public class EventValidation {

    /**
     * Validates the event title.
     *
     * @param title    The TextField containing the event's title.
     * @param response The Label used to display validation messages.
     * @return true if the title is valid, false otherwise.
     */
    private static boolean validateTitle(TextField title, Label response) {
        boolean isTitleValid = ValidationRules.isValidTitle(title.getText());
        ViewHelpers.validateField(title, isTitleValid, "Invalid title", response);
        return isTitleValid;
    }

    /**
     * Validates the event date.
     *
     * @param date     The DatePicker containing the event's date.
     * @param response The Label used to display validation messages.
     * @return true if the date is valid, false otherwise.
     */
    private static boolean validateDate(DatePicker date, Label response) {
        boolean isDateValid = ValidationRules.isValidDate(date);
        ViewHelpers.validateField(date, isDateValid, "Invalid date", response);
        return isDateValid;
    }

    /**
     * Validates the event category.
     *
     * @param category The ComboBox containing the event's category.
     * @param response The Label used to display validation messages.
     * @return true if the category is valid, false otherwise.
     */
    private static boolean validateCategory(ComboBox<Category> category, Label response) {
        boolean isCategoryValid = ValidationRules.isValidComboBox(category);
        ViewHelpers.validateField(category, isCategoryValid, "Invalid category", response);
        return isCategoryValid;
    }

    /**
     * Validates the entire Event form with title, date, and category.
     *
     * @param title      The TextField containing the event's title.
     * @param date       The DatePicker containing the event's date.
     * @param categoryItem The ComboBox containing the event's category.
     * @param response   The Label used to display validation messages.
     * @return true if all fields are valid, false otherwise.
     */
    public static boolean validateForm(TextField title, DatePicker date, ComboBox<Category> categoryItem, Label response) {
        ViewHelpers.clearResponseMessage(response);
        return validateTitle(title, response) && validateCategory(categoryItem, response) && validateDate(date, response);
    }

    /**
     * Validates the Event form with title and date, excluding category.
     *
     * @param title    The TextField containing the event's title.
     * @param date     The DatePicker containing the event's date.
     * @param response The Label used to display validation messages.
     * @return true if both fields are valid, false otherwise.
     */
    public static boolean validateForm(TextField title, DatePicker date, Label response) {
        ViewHelpers.clearResponseMessage(response);
        return validateTitle(title, response) && validateDate(date, response);
    }

    /**
     * Validates the Event form with only the date.
     *
     * @param date     The DatePicker containing the event's date.
     * @param response The Label used to display validation messages.
     * @return true if the date is valid, false otherwise.
     */
    public static boolean validateForm(DatePicker date, Label response) {
        ViewHelpers.clearResponseMessage(response);
        return validateDate(date, response);
    }
}
