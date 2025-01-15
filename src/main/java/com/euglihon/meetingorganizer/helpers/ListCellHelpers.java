package com.euglihon.meetingorganizer.helpers;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.model.enums.Color;
import javafx.scene.control.ListCell;
import javafx.scene.shape.Circle;

import java.util.List;

/**
 * Helper class for creating custom ListCells.
 * These cells are used to display information in ComboBoxes and Lists with customized graphics (like color circles).
 */
public class ListCellHelpers {

    /**
     * Sets an empty ListCell with no text or graphic.
     *
     * @param cell the ListCell to be set empty
     */
    private static void setEmpty(ListCell<?> cell) {
        cell.setText(null);
        cell.setGraphic(null);
    }

    /**
     * Creates a Circle with a color and radius.
     *
     * @param colorCode the color code for the circle
     * @param radius the radius of the circle
     * @return a Circle with the specified color and radius
     */
    private static Circle createColorCircle(String colorCode, double radius) {
        Circle circle = new Circle(radius);
        circle.setStyle("-fx-fill: " + colorCode + ";");
        return circle;
    }

    /**
     * Returns a ListCell for displaying a Category with its associated color as a circle.
     *
     * @return a ListCell that displays Category with its color circle
     */
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

    /**
     * Returns a ListCell for displaying a Contact with its associated Category's color as a circle.
     *
     * @param categories the list of categories for finding the category of the contact
     * @return a ListCell that displays Contact with its category's color circle
     */
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

    /**
     * Returns a ListCell for displaying a Contact in a contact list with category color as a circle.
     *
     * @param categories the list of categories for finding the category of the contact
     * @return a ListCell that displays Contact with its category's color circle
     */
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

    /**
     * Returns a ListCell for displaying an Event with its associated Category's color as a circle.
     *
     * @param categories the list of categories for finding the category of the event
     * @return a ListCell that displays Event with its category's color circle
     */
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

    /**
     * Returns a ListCell for displaying a Color with its color as a circle.
     *
     * @return a ListCell that displays Color with its circle
     */
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



