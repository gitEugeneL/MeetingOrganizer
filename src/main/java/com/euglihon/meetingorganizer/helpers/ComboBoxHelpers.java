package com.euglihon.meetingorganizer.helpers;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import javafx.scene.control.ComboBox;

import java.util.List;

/**
 * Helper methods for configuration ComboBoxes with Category and Contact data.
 */
public class ComboBoxHelpers {


    /**
     * Sets up the ComboBox for selecting a Category.
     * Adds categories to the ComboBox, and customizes the cell rendering and button cell.
     *
     * @param categories the list of categories to populate the ComboBox
     * @param categoryComboBox the ComboBox to be populated with categories
     */
    public static void setupCategoryComboBox(List<Category> categories, ComboBox<Category> categoryComboBox) {
        // Add all categories to the ComboBox
        categoryComboBox.getItems().addAll(categories);
        // Set custom cell factory for displaying categories with their associated colors
        categoryComboBox.setCellFactory(comboBox -> ListCellHelpers.categoryWithColorComboBox());
        // Set the button cell (the cell that shows when the ComboBox is not expanded)
        categoryComboBox.setButtonCell(ListCellHelpers.categoryWithColorComboBox());
    }

    /**
     * Sets up the ComboBox for selecting a Category for category item
     * This ComboBox is populated with the provided categories and selects the first item by default if available.
     *
     * @param categories the list of categories to populate the ComboBox
     * @param categoryItemComboBox the ComboBox to be populated with categories
     */
    public static void setupCategoryItemComboBox(List<Category> categories, ComboBox<Category> categoryItemComboBox) {
        // Set the list of categories to the ComboBox
        categoryItemComboBox.getItems().setAll(categories);
        // If there are categories, select the first one by default
        if (!categories.isEmpty()) {
            categoryItemComboBox.setValue(categoryItemComboBox.getItems().get(0));
        }
        // Set custom cell factory for displaying categories with their associated colors
        categoryItemComboBox.setCellFactory(c -> ListCellHelpers.categoryWithColorComboBox());
        // Set the button cell (the cell that shows when the ComboBox is not expanded)
        categoryItemComboBox.setButtonCell(ListCellHelpers.categoryWithColorComboBox());
    }

    /**
     * Sets up the ComboBox for selecting a Contact.
     * This ComboBox is populated with the provided contacts and custom cell rendering.
     *
     * @param contacts the list of contacts to populate the ComboBox
     * @param categories the list of categories to provide contact colors
     * @param participantsComboBox the ComboBox to be populated with contacts
     */
    public static void setupParticipantsComboBox(List<Contact> contacts, List<Category> categories, ComboBox<Contact> participantsComboBox) {
        // Remove all existing items in the ComboBox (if any)
        participantsComboBox.getItems().removeAll(participantsComboBox.getItems());
        // Add all contacts to the ComboBox
        participantsComboBox.getItems().addAll(contacts);
        // Set custom cell factory for displaying contacts with their associated colors
        participantsComboBox.setCellFactory(c -> ListCellHelpers.contactWithColorComboBox(categories));
        // Set the button cell (the cell that shows when the ComboBox is not expanded)
        participantsComboBox.setButtonCell(ListCellHelpers.contactWithColorComboBox(categories));
    }
}
