package com.euglihon.meetingorganizer.helpers;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.model.enums.Color;
import javafx.scene.control.ListCell;
import javafx.scene.shape.Circle;

import java.util.List;

public class ListCellHelpers {
    private static void setEmpty(ListCell<?> cell) {
        cell.setText(null);
        cell.setGraphic(null);
    }

    private static Circle createColorCircle(String colorCode, double radius) {
        Circle circle = new Circle(radius);
        circle.setStyle("-fx-fill: " + colorCode + ";");
        return circle;
    }

    public static ListCell<Category> categoryWithColorComboBox() {
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

    public static ListCell<Contact> contactWithColorComboBox(List<Category> categories) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setEmpty(this);
                } else {
                    Category category = contact.getCategory(categories);
                    Circle circle = createColorCircle(category.getColorCode(), 8);
                    setText(contact.getFirstName() + " " + contact.getLastName());
                    setGraphic(circle);
                }
            }
        };
    }

    public static ListCell<Contact> contactWithCategoryContactList(List<Category> categories) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setEmpty(this);
                } else {
                    Category category = contact.getCategory(categories);
                    Circle circle = createColorCircle(category.getColorCode(), 8);
                    setText(contact.getFirstName() + " | " + contact.getLastName() + " | " + contact.getPhone());
                    setGraphic(circle);
                }
            }
        };
    }

    public static ListCell<Event> eventWithCategoryContactList(List<Category> categories) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setEmpty(this);
                } else {
                    Category category = event.getCategory(categories);
                    Circle circle = createColorCircle(category.getColorCode(), 8);
                    setText(event.getTitle() + " | " + event.getDate().toString());
                    setGraphic(circle);
                }
            }
        };
    }

    public static ListCell<Color> colorNameWithColorCircle() {
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
}



