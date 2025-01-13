package com.euglihon.meetingorganizer.helpers;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import javafx.scene.control.ComboBox;

import java.util.List;

public class ComboBoxHelpers {

    public static void setupCategoryComboBox(List<Category> categories, ComboBox<Category> categoryComboBox) {
        categoryComboBox.getItems().addAll(categories);
        categoryComboBox.setCellFactory(comboBox -> ListCellHelpers.categoryWithColorComboBox());
        categoryComboBox.setButtonCell(ListCellHelpers.categoryWithColorComboBox());
    }

    public static void setupCategoryItemComboBox(List<Category> categories, ComboBox<Category> categoryItemComboBox) {
        categoryItemComboBox.getItems().setAll(categories);
        if (!categories.isEmpty()) {
            categoryItemComboBox.setValue(categoryItemComboBox.getItems().get(0));
        }
        categoryItemComboBox.setCellFactory(c -> ListCellHelpers.categoryWithColorComboBox());
        categoryItemComboBox.setButtonCell(ListCellHelpers.categoryWithColorComboBox());
    }

    public static void setupParticipantsComboBox(List<Contact> contacts, List<Category> categories, ComboBox<Contact> participantsComboBox) {
        participantsComboBox.getItems().removeAll(participantsComboBox.getItems());
        participantsComboBox.getItems().addAll(contacts);

        participantsComboBox.setCellFactory(c -> ListCellHelpers.contactWithColorComboBox(categories));
        participantsComboBox.setButtonCell(ListCellHelpers.contactWithColorComboBox(categories));
    }
}
