package com.euglihon.meetingorganizer.helpers;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public final class ViewHelpers {

    public static boolean validateField(Control field, boolean isValid, String error, Label response) {
        if (isValid) {
            changeInputBorder(field, true);
        } else {
            createResponseMessage(response, error);
            changeInputBorder(field, false);
        }
        return isValid;
    }

    public static void showContainer(Pane container) {
        container.setManaged(true);
        container.setVisible(true);
    }

    public static void disableContainer(Pane container) {
        container.setManaged(false);
        container.setVisible(false);
    }

    public static void changeInputBorder(Control field, boolean valid) {
        if (valid) {
            field.setStyle("-fx-border-color: none");
        } else {
            field.setStyle("-fx-border-color: red");
        }
    }

    public static void clearInputFields(TextField... fields) {
        String style = "-fx-border-color: none";
        for (TextField field : fields) {
            field.setText("");
            field.setStyle(style);
        }
    }

    public static void clearResponseMessage(Label label) {
            label.setText("");
    }

    public static void createResponseMessage(Label label, String message) {
        label.setText(message);
    }
}
