package com.euglihon.meetingorganizer.controller;

import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.validation.UIDataValidation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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

    @FXML private ListView<Contact> contactListView;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private ComboBox<Category> categoryItemComboBox;
    @FXML private TextField firstNameTextField, lastNameTextField, phoneTextField;
    @FXML private HBox buttonGroupField;
    @FXML private Label responseLabel;

    @FXML private void initialize() {
        this.loadCategories();
        this.loadAllContacts();
        this.refreshContactList(this.contacts);
        this.setupCategoryItemComboBox();
        this.setupCategoryComboBox();
        this.createButton("Create");
    }

    @FXML private void contactListForm() {
        Contact contact = this.contactListView.getSelectionModel().getSelectedItem();
        if (contact != null) {
            this.populateForm(contact);
            this.updateViewForEdit();
        }
    }

    @FXML private void filterContacts() {
        Category selectedCategory = this.categoryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        this.loadAllContactsByCategoryId(selectedCategory.getId());
        this.refreshContactList(this.contacts);
    }

    @FXML private void clearFilterContacts() {
        this.loadAllContacts();
        this.categoryComboBox.setValue(null);
        this.refreshContactList(this.contacts);
    }

    @FXML private void addElement() {
        if (!this.validateFields()) return;
        Contact contact = this.createContactFromForm();
        if (!contactService.addContact(contact)) {
            ViewHelpers.CreateResponseMessage(responseLabel, "Contact already exists");
        } else {
            this.resetForm();
            this.loadAllContacts();
            this.refreshContactList(this.contacts);
        }
    }

    @FXML private void editElement() {
        if (!this.validateFields()) return;
        Contact contact = this.createContactFromForm();
        contact.setId(contactListView.getSelectionModel().getSelectedItem().getId());
        if (!contactService.updateContact(contact)) {
            ViewHelpers.CreateResponseMessage(responseLabel, "Error updating contact");
        } else {
            this.resetForm();
            this.loadAllContacts();
            this.refreshContactList(this.contacts);
        }
    }

    @FXML private void deleteElement() {
        int contactId = contactListView.getSelectionModel().getSelectedItem().getId();
        contactService.deleteContactById(contactId);
        this.loadAllContacts();
        this.refreshContactList(this.contacts);
        this.exitEditing();
    }

    @FXML private void exitEditing() {
        this.resetForm();
        this.updateViewForAdd();
    }

    private void loadAllContacts() {
        this.contacts = this.contactService.getAllContacts();
    }

    private void loadAllContactsByCategoryId(int categoryId) {
        this.contacts = this.contactService.getAllContactsByCategoryId(categoryId);
    }

    private void loadCategories() {
        this.categories = this.categoryService.getAllCategories();
    }

    private void setupCategoryItemComboBox() {
        this.categoryItemComboBox.getItems().setAll(this.categories);
        this.categoryItemComboBox.setValue(this.categoryItemComboBox.getItems().get(0));
        this.categoryItemComboBox.setCellFactory(comboBox -> ViewHelpers.CategoryWithColorComboBox());
        this.categoryItemComboBox.setButtonCell(ViewHelpers.CategoryWithColorComboBox());
    }

    private void setupCategoryComboBox() {
        this.categoryComboBox.getItems().setAll(this.categories);
        this.categoryComboBox.setCellFactory(comboBox -> ViewHelpers.CategoryWithColorComboBox());
        this.categoryComboBox.setButtonCell(ViewHelpers.CategoryWithColorComboBox());
    }

    private void refreshContactList(List<Contact> contacts) {
        this.contactListView.getItems().setAll(contacts);
        this.contactListView.setCellFactory(lv -> ViewHelpers.ContactWithCategoryContactList(categories));
    }

    private void createButton(String buttonName) {
        Button button = new Button(buttonName);
        button.setOnAction(event -> this.handleButtonAction(buttonName));
        this.buttonGroupField.getChildren().add(button);
    }

    private void handleButtonAction(String buttonName) {
        switch (buttonName) {
            case "Create": this.addElement(); break;
            case "Edit": this.editElement(); break;
            case "Delete": this.deleteElement(); break;
            case "Exit": this.exitEditing(); break;
        }
    }

    private boolean validateFields() {
        ViewHelpers.ClearResponseMessage(this.responseLabel);
        boolean isValid = true;
        if (!UIDataValidation.isNameOrSurname(this.firstNameTextField.getText())) {
            ViewHelpers.CreateResponseMessage(this.responseLabel, "Invalid first name");
            ViewHelpers.ChangeTextFieldBorderColor(this.firstNameTextField, false);
            isValid = false;
        }
        if (!UIDataValidation.isNameOrSurname(this.lastNameTextField.getText())) {
            ViewHelpers.CreateResponseMessage(this.responseLabel, "Invalid last name");
            ViewHelpers.ChangeTextFieldBorderColor(this.lastNameTextField, false);
            isValid = false;
        }
        if (!UIDataValidation.isValidPhoneNumber(this.phoneTextField.getText())) {
            ViewHelpers.CreateResponseMessage(this.responseLabel, "Invalid phone number format");
            ViewHelpers.ChangeTextFieldBorderColor(this.phoneTextField, false);
            isValid = false;
        }
        return isValid;
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
        ViewHelpers.ClearInputFields(this.firstNameTextField, this.lastNameTextField, this.phoneTextField);
        ViewHelpers.ClearResponseMessage(this.responseLabel);
    }

    private void updateViewForEdit() {
        this.buttonGroupField.getChildren().clear();
        this.createButton("Exit");
        this.createButton("Edit");
        this.createButton("Delete");
    }

    private void updateViewForAdd() {
        this.buttonGroupField.getChildren().clear();
        this.createButton("Create");
    }
}
