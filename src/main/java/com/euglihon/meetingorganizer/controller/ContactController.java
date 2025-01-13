package com.euglihon.meetingorganizer.controller;

import com.euglihon.meetingorganizer.helpers.ComboBoxHelpers;
import com.euglihon.meetingorganizer.helpers.ListCellHelpers;
import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.validation.ContactValidation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ContactController {

    private final IContactService contactService;
    private final ICategoryService categoryService;

    public ContactController(IContactService contactService, ICategoryService categoryService) {
        this.contactService = contactService;
        this.categoryService = categoryService;
    }

    private List<Category> categories;
    private List<Contact> contacts;

    @FXML
    private ListView<Contact> contactListView;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private ComboBox<Category> categoryItemComboBox;
    @FXML
    private TextField firstNameTextField, lastNameTextField, phoneTextField;
    @FXML
    private HBox addContainer, updateContainer;
    @FXML
    private VBox categoryContainer;
    @FXML
    private Label responseLabel;

    @FXML
    private void initialize() {
        this.loadAllCategories();
        this.loadAllContacts();
        this.refreshContactList(this.contacts);
        ComboBoxHelpers.setupCategoryItemComboBox(this.categories, this.categoryItemComboBox);
        ComboBoxHelpers.setupCategoryComboBox(this.categories, this.categoryComboBox);
        ViewHelpers.showContainer(this.addContainer);
    }

    @FXML
    private void contactListForm() {
        this.resetForm();
        Contact selectedContact = this.contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            return;
        }
        this.contactAssociatedWithEvent(selectedContact.getId());
        this.populateForm(selectedContact);
        this.toggleEditingMode(true);
    }

    @FXML
    private void filterContacts() {
        Category selectedCategory = this.categoryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        this.loadAllContactsByCategoryId(selectedCategory.getId());
        this.refreshContactList(this.contacts);
    }

    @FXML
    private void clearFilterContacts() {
        this.loadAllContacts();
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.refreshContactList(this.contacts);
    }

    @FXML
    private void addContact() {
        if (!ContactValidation.validateForm(this.firstNameTextField,
                this.lastNameTextField, this.phoneTextField, this.categoryItemComboBox, this.responseLabel)) {
            return;
        }
        Contact contact = this.createContactFromForm();
        if (!this.contactService.addContact(contact)) {
            ViewHelpers.createResponseMessage(responseLabel, "Contact already exists");
        } else {
            this.resetForm();
            this.loadAllContacts();
            this.refreshContactList(this.contacts);
        }
    }

    @FXML
    private void editContact() {
        if (!ContactValidation.validateForm(this.firstNameTextField,
                this.lastNameTextField, this.phoneTextField, this.categoryItemComboBox, this.responseLabel)) {
            return;
        }
        Contact contact = this.createContactFromForm();
        contact.setId(contactListView.getSelectionModel().getSelectedItem().getId());
        if (!contactService.updateContact(contact)) {
            ViewHelpers.createResponseMessage(responseLabel, "Phone is already used by another contact");
        } else {
            this.resetForm();
            this.loadAllContacts();
            this.refreshContactList(this.contacts);
            this.exitEditing();
        }
    }

    @FXML
    private void deleteContact() {
        int contactId = contactListView.getSelectionModel().getSelectedItem().getId();
        contactService.deleteContactById(contactId);
        this.loadAllContacts();
        this.refreshContactList(this.contacts);
        this.exitEditing();
    }

    @FXML
    private void exitEditing() {
        this.resetForm();
        this.toggleEditingMode(false);
    }

    private void loadAllContacts() {
        this.contacts = this.contactService.getAllContacts();
    }

    private void loadAllContactsByCategoryId(int categoryId) {
        this.contacts = this.contactService.getAllContactsByCategoryId(categoryId);
    }

    private void loadAllCategories() {
        this.categories = this.categoryService.getAllCategories();
    }

    private void refreshContactList(List<Contact> contacts) {
        this.contactListView.getItems().setAll(contacts);
        this.contactListView.setCellFactory(lv -> ListCellHelpers.contactWithCategoryContactList(this.categories));
    }

    private Contact createContactFromForm() {
        Contact contact = new Contact();
        contact.setFirstName(this.firstNameTextField.getText().trim());
        contact.setLastName(this.lastNameTextField.getText().trim());
        contact.setPhone(this.phoneTextField.getText().trim());
        contact.setCategoryId(this.categoryItemComboBox.getValue().getId());
        return contact;
    }

    private void populateForm(Contact contact) {
        this.firstNameTextField.setText(contact.getFirstName());
        this.lastNameTextField.setText(contact.getLastName());
        this.phoneTextField.setText(contact.getPhone());
        this.categoryItemComboBox.setValue(contact.getCategory(this.categories));
    }

    private void resetForm() {
        this.toggleEditingMode(false);
        ViewHelpers.clearInputFields(this.firstNameTextField, this.lastNameTextField, this.phoneTextField);
        ViewHelpers.clearResponseMessage(this.responseLabel);
    }

    private void contactAssociatedWithEvent(int contactId) {
        boolean isContactAssociated = this.contactService.isContactAssociatedWithEvent(contactId);
        if (isContactAssociated) {
            ViewHelpers.disableContainer(this.categoryContainer);
        } else {
            ViewHelpers.showContainer(this.categoryContainer);
        }
    }

    private void toggleEditingMode(boolean isEditing) {
        if (isEditing) {
            ViewHelpers.disableContainer(addContainer);
            ViewHelpers.showContainer(updateContainer);
        } else {
            ViewHelpers.showContainer(addContainer);
            ViewHelpers.disableContainer(updateContainer);
        }
    }
}
