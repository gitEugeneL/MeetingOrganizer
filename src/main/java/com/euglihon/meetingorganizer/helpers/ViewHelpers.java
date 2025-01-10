package com.euglihon.meetingorganizer.helpers;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.model.enums.Color;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.util.List;

public final class ViewHelpers {

    public static void changeInputBorder(Control field, boolean valid) {
        if (valid) {
            field.setStyle("-fx-border-color: none");
        } else {
            field.setStyle("-fx-border-color: red");
        }
    }

    private static void setEmpty(ListCell<?> cell) {
        cell.setText(null);
        cell.setGraphic(null);
    }

    private static Circle createColorCircle(String colorCode, double radius) {
        Circle circle = new Circle(radius);
        circle.setStyle("-fx-fill: " + colorCode + ";");
        return circle;
    }

    public static ListCell<Category> CategoryWithColorComboBox() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty || category == null) {
                    setEmpty(this);
                } else {
                    Circle circle = createColorCircle(category.getColorCode(), 8);
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
                    setEmpty(this);
                } else {
                    Category category = contact.getCategory(categories);
                    Circle circle = createColorCircle(category.getColorCode(), 8);
                    Label contactLabel = new Label(contact.getFirstName() + " | " + contact.getLastName() + " | " + contact.getPhone());
                    contactLabel.setStyle("-fx-text-fill: white;");
                    HBox hBox = new HBox(10, circle, contactLabel);
                    setGraphic(hBox);
                }
            }
        };
    }

    public static ListCell<Event> EventWithCategoryContactList(List<Category> categories) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setEmpty(this);
                } else {
                    Category category = event.getCategory(categories);
                    Circle circle = createColorCircle(category.getColorCode(), 8);
                    Label eventLabel = new Label(event.getTitle() + " | " + event.getDate().toString());
                    eventLabel.setStyle("-fx-text-fill: white;");
                    HBox hBox = new HBox(10, circle, eventLabel);
                    setGraphic(hBox);
                }
            }
        };
    }

    public static ListCell<Color> ColorNameWithColorCircle() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);
                if (empty || color == null) {
                    setEmpty(this);
                } else {
                    Category category = new Category();
                    category.setColor(color);
                    Circle colorCircle = createColorCircle(category.getColorCode(), 10);
                    setGraphic(colorCircle);
                    setText(color.toString());
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
