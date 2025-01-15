package com.euglihon.meetingorganizer.helpers;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Utility class providing helper methods for handling UI-related operations,
 * such as validating fields, changing styles, and managing containers.
 */
public final class ViewHelpers {

    /**
     * Validates a field and updates its border color based on the validation result.
     * If the field is invalid, an error message will be displayed.
     *
     * @param field the UI control (TextField, etc.) to be validated
     * @param isValid the validation result (true for valid, false for invalid)
     * @param error the error message to be displayed if the field is invalid
     * @param response the label where the error message will be displayed
     * @return true if the field is valid, false if invalid
     */
    public static boolean validateField(Control field, boolean isValid, String error, Label response) {
        if (isValid) {
            // Set border to normal if valid
            changeInputBorder(field, true);
        } else {
            // Show error message
            createResponseMessage(response, error);
            // Set border to red if invalid
            changeInputBorder(field, false);
        }
        return isValid;
    }

    /**
     * Makes the specified container visible and managed.
     *
     * @param container the container (Pane) to be shown
     */
    public static void showContainer(Pane container) {
        container.setManaged(true);
        container.setVisible(true);
    }

    /**
     * Hides the specified container and removes it from the layout.
     *
     * @param container the container (Pane) to be hidden
     */
    public static void disableContainer(Pane container) {
        container.setManaged(false);
        container.setVisible(false);
    }

    /**
     * Changes the border style of an input field to indicate whether it is valid.
     * If valid, removes the border; if invalid, sets the border to red.
     *
     * @param field the input field whose border style is to be changed
     * @param valid true if the field is valid, false if invalid
     */
    public static void changeInputBorder(Control field, boolean valid) {
        if (valid) {
            field.setStyle("-fx-border-color: none");
        } else {
            field.setStyle("-fx-border-color: red");
        }
    }

    /**
     * Clears the text and resets the style for multiple TextField inputs.
     *
     * @param fields the TextField inputs to be cleared
     */
    public static void clearInputFields(TextField... fields) {
        String style = "-fx-border-color: none";
        for (TextField field : fields) {
            field.setText("");
            field.setStyle(style);
        }
    }

    /**
     * Clears the text in the specified response label.
     *
     * @param label the label to be cleared
     */
    public static void clearResponseMessage(Label label) {
        label.setText("");
    }


    /**
     * Sets the specified message in the response label.
     *
     * @param label the label where the message will be displayed
     * @param message the message to be set
     */
    public static void createResponseMessage(Label label, String message) {
        label.setText(message);
    }
}
