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

    /**
     * Constructor to initialize ContactController with contact and category services.
     *
     * @param contactService  the service responsible for contact operations
     * @param categoryService the service responsible for category operations
     */
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

    /**
     * Initializes the controller
     */
    @FXML
    private void initialize() {
        // Load categories from the service
        this.loadAllCategories();
        // Load contacts from the service
        this.loadAllContacts();
        // Refresh the contact list view
        this.refreshContactList(this.contacts);
        // Setup category item combo box
        ComboBoxHelpers.setupCategoryItemComboBox(this.categories, this.categoryItemComboBox);
        // Setup category combo box
        ComboBoxHelpers.setupCategoryComboBox(this.categories, this.categoryComboBox);
        // Show the add contact container
        ViewHelpers.showContainer(this.addContainer);
    }

    /**
     * Prepares the form for editing an existing contact.
     * If no contact is selected, it does nothing.
     */
    @FXML
    private void contactListForm() {
        // Reset the form to clear any existing data
        this.resetForm();
        Contact selectedContact = this.contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            return;
        }
        // Check if the contact is associated with an event
        this.contactAssociatedWithEvent(selectedContact.getId());
        // Populate the form with the contact's details
        this.populateForm(selectedContact);
        // Switch to editing mode
        this.toggleEditingMode(true);
    }

    /**
     * Filters the contact list based on the selected category.
     */
    @FXML
    private void filterContacts() {
        Category selectedCategory = this.categoryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        // Load contacts by category ID
        this.loadAllContactsByCategoryId(selectedCategory.getId());
        // Refresh the contact list view
        this.refreshContactList(this.contacts);
    }

    /**
     * Clears the filter and reloads all contacts.
     */
    @FXML
    private void clearFilterContacts() {
        // Load all contacts
        this.loadAllContacts();
        // Clear the category filter
        this.categoryComboBox.getSelectionModel().clearSelection();
        // Refresh the contact list view
        this.refreshContactList(this.contacts);
    }

    /**
     * Adds a new contact by validating the form and submitting it.
     * If the contact already exists, an error message is shown.
     */
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

    /**
     * Edits an existing contact by validating the form and updating it.
     * If the phone number is already used, an error message is shown.
     */
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

    /**
     * Deletes the selected contact from the list and updates the list view.
     */
    @FXML
    private void deleteContact() {
        int contactId = contactListView.getSelectionModel().getSelectedItem().getId();
        contactService.deleteContactById(contactId);
        this.loadAllContacts();
        this.refreshContactList(this.contacts);
        this.exitEditing();
    }

    /**
     * Exits the editing mode and resets the form.
     */
    @FXML
    private void exitEditing() {
        this.resetForm();
        this.toggleEditingMode(false);
    }

    /**
     * Loads all contacts from the contact service.
     */
    private void loadAllContacts() {
        this.contacts = this.contactService.getAllContacts();
    }

    /**
     * Loads contacts filtered by category ID.
     *
     * @param categoryId the category ID to filter contacts by
     */
    private void loadAllContactsByCategoryId(int categoryId) {
        this.contacts = this.contactService.getAllContactsByCategoryId(categoryId);
    }

    /**
     * Loads all categories from the category service.
     */
    private void loadAllCategories() {
        this.categories = this.categoryService.getAllCategories();
    }

    /**
     * Refreshes the contact list view with the provided list of contacts.
     *
     * @param contacts the list of contacts to display
     */
    private void refreshContactList(List<Contact> contacts) {
        this.contactListView.getItems().setAll(contacts);
        this.contactListView.setCellFactory(lv -> ListCellHelpers.contactWithCategoryContactList(this.categories));
    }

    /**
     * Creates a new contact object based on the form data.
     *
     * @return a new Contact object
     */
    private Contact createContactFromForm() {
        Contact contact = new Contact();
        contact.setFirstName(this.firstNameTextField.getText().trim());
        contact.setLastName(this.lastNameTextField.getText().trim());
        contact.setPhone(this.phoneTextField.getText().trim());
        contact.setCategoryId(this.categoryItemComboBox.getValue().getId());
        return contact;
    }

    /**
     * Populates the form fields with the selected contact's data.
     *
     * @param contact the contact to populate the form with
     */
    private void populateForm(Contact contact) {
        this.firstNameTextField.setText(contact.getFirstName());
        this.lastNameTextField.setText(contact.getLastName());
        this.phoneTextField.setText(contact.getPhone());
        this.categoryItemComboBox.setValue(contact.getCategory(this.categories));
    }

    /**
     * Resets the form by clearing input fields and response messages.
     */
    private void resetForm() {
        this.toggleEditingMode(false);
        ViewHelpers.clearInputFields(this.firstNameTextField, this.lastNameTextField, this.phoneTextField);
        ViewHelpers.clearResponseMessage(this.responseLabel);
    }

    /**
     * Checks if a contact is associated with an event.
     * If yes, disables the category selection container; otherwise, shows it.
     *
     * @param contactId the contact ID to check
     */
    private void contactAssociatedWithEvent(int contactId) {
        boolean isContactAssociated = this.contactService.isContactAssociatedWithEvent(contactId);
        if (isContactAssociated) {
            ViewHelpers.disableContainer(this.categoryContainer);
        } else {
            ViewHelpers.showContainer(this.categoryContainer);
        }
    }

    /**
     * Toggles between the add and update containers based on the editing mode.
     *
     * @param isEditing true to switch to editing mode, false for add mode
     */
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
