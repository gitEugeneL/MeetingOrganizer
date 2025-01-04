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
import java.awt.event.ActionEvent;
import java.util.List;

public class ContactController {

    private final IContactService contactService;
    private final ICategoryService categoryService;
    public ContactController(IContactService contactService, ICategoryService categoryService) {
        this.contactService = contactService;
        this.categoryService = categoryService;
    }

    private final String addButton = "Create";
    private final String editButton = "Edit";
    private final String deleteButton = "Delete";
    private final String closeButton = "Exit";

    private List<Category> categories;

    @FXML
    private ListView<Contact> contactListView;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private HBox buttonGroupField;

    @FXML
    private Label responseLabel;

    @FXML
    private void initialize() {
        this.getCategories();
        this.createDynamicButton(addButton);
        this.refreshContactList();
        this.refreshCategoryComboBox();
    }

    @FXML
    private void addElement() {

        if (!this.validateFields()) {
            return;
        }
        Contact contact = new Contact();
        contact.setFirstName(this.firstNameTextField.getText().trim());
        contact.setLastName(this.lastNameTextField.getText().trim());
        contact.setPhone(this.phoneTextField.getText().trim());
        Category selectedCategory = this.categoryComboBox.getValue();
        contact.setCategoryId(selectedCategory.getId());

        boolean result = this.contactService.addContact(contact);
        if (!result) {
            this.responseLabel.setText("Contact " + contact.getPhone() + " already exists");
        } else {
            this.refreshContactList();
            this.clearInputFields();
        }
    }

    @FXML
    private void editElement(ActionEvent event) {
        // todo
    }

    @FXML
    private void deleteElement(ActionEvent event) {
        // todo
    }


    private void getCategories() {
        this.categories = this.categoryService.getAllCategories();
    }

    private void refreshContactList() {
        contactListView.getItems().clear();
        contactListView.getItems().addAll(contactService.getAllContacts());
        // Custom list
        contactListView.setCellFactory(lv -> ViewHelpers.UpdateContactList(this.categories));
    }

    private void refreshCategoryComboBox() {
        categoryComboBox.getItems().addAll(categories);
        // Set default category
        Category defaultCategory = this.categoryComboBox.getItems().getFirst();
        this.categoryComboBox.setValue(defaultCategory);
        // Custom comboBox
        categoryComboBox.setCellFactory(comboBox -> ViewHelpers.UpdateCategoryComboBox());
        categoryComboBox.setButtonCell(ViewHelpers.UpdateCategoryComboBox());
    }

    private void createDynamicButton(String buttonName) {
        Button button = new Button(buttonName);
        switch (buttonName) {
            case addButton:
                button.setOnAction(event -> addElement());
                break;
            default:
                return;
        }
        this.buttonGroupField.getChildren().add(button);
    }

    private boolean validateFields() {
        this.responseLabel.setText("");

        boolean isFirstNameValid = UIDataValidation.isNameOrSurname(this.firstNameTextField.getText());
        boolean isLastNameValid = UIDataValidation.isNameOrSurname(this.lastNameTextField.getText());
        boolean isPhoneValid = UIDataValidation.isValidPhoneNumber(this.phoneTextField.getText());

        if (!isFirstNameValid)
            this.responseLabel.setText("First name is invalid");
        if (!isLastNameValid)
            this.responseLabel.setText("Last name is invalid");
        if (!isPhoneValid)
            this.responseLabel.setText("Phone number is invalid. Valid format: +48-000-000-000");

        ViewHelpers.changeTextFieldBorderColor(this.firstNameTextField, isFirstNameValid);
        ViewHelpers.changeTextFieldBorderColor(this.lastNameTextField, isLastNameValid);
        ViewHelpers.changeTextFieldBorderColor(this.phoneTextField, isPhoneValid);

        return isFirstNameValid && isLastNameValid && isPhoneValid;
    }

    private void clearInputFields() {
        this.responseLabel.setText("");
        this.firstNameTextField.setText("");
        this.lastNameTextField.setText("");
        this.phoneTextField.setText("");
        this.phoneTextField.setText("");
    }
}
