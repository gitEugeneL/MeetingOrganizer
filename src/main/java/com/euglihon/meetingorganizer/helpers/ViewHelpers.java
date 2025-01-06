package com.euglihon.meetingorganizer.helpers;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.util.List;

public final class ViewHelpers {

    public static void ChangeTextFieldBorderColor(TextField textField, boolean valid) {
        if (valid) {
            textField.setStyle("-fx-border-color: none");
        } else {
            textField.setStyle("-fx-border-color: red");
        }
    }

    public static ListCell<Category> CategoryWithColorComboBox() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty || category == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Circle circle = new Circle(8);
                    circle.setStyle("-fx-fill: " + category.getColor() + ";");
                    setText(category.getName());
                    setGraphic(circle);
                }
            }
        };
    }

    public static ListCell<Contact> ContactWithCategoryContactList(List<Category> categories) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Category category = contact.getCategory(categories);

                    Circle circle = new Circle(8);
                    circle.setStyle("-fx-fill: " + category.getColor() + ";");
                    Label contactLabel = new Label(contact.getFirstName()
                            + " | " + contact.getLastName() + " | " + contact.getPhone());
                    contactLabel.setStyle("-fx-text-fill: white;");
                    HBox hBox = new HBox(10, circle, contactLabel);
                    setGraphic(hBox);
                }
            }
        };
    }

    public static void ClearInputFields(TextField... fields) {
        String style = "-fx-border-color: none";
        for (TextField field : fields) {
            field.setText("");
            field.setStyle(style);
        }
    }

    public static void ClearResponseMessage(Label label) {
            label.setText("");
    }

    public static void CreateResponseMessage(Label label, String message) {
        label.setText(message);
    }
}
