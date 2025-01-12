package com.euglihon.meetingorganizer.controller;

import com.euglihon.meetingorganizer.helpers.ComboBoxHelpers;
import com.euglihon.meetingorganizer.helpers.ListCellHelpers;
import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.service.IEventService;
import com.euglihon.meetingorganizer.validation.EventValidation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;

public class EventController {

    private final IEventService eventService;
    private final ICategoryService categoryService;
    private final IContactService contactService;
    public EventController(IEventService eventService, ICategoryService categoryService, IContactService contactService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.contactService = contactService;
    }

    private List<Contact> eventContacts;
    private List<Contact> contactsToAdd;

    private List<Category> categories;
    private List<Event> events;

    @FXML
    private ListView<Event> eventListView;
    @FXML
    private ListView<Contact> eventContactListView;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private ComboBox<Category> categoryItemComboBox;
    @FXML
    private ComboBox<Contact> participantsComboBox;
    @FXML
    private VBox addContainer, participantsContainer;
    @FXML
    private TextField titleTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label responseLabel;

    @FXML
    private void initialize() {
        this.loadAllCategories();
        this.loadAllEvents();
        this.refreshEventList(this.events);
        ComboBoxHelpers.setupCategoryItemComboBox(this.categories, this.categoryComboBox);
        ComboBoxHelpers.setupCategoryComboBox(this.categories, this.categoryItemComboBox);
        this.clearFilterEvents();
    }

    @FXML
    private void eventListForm() {
        this.createParticipantsContainer();
    }

    @FXML
    private void filterEvents() {
        Category selectedCategory = this.categoryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        this.exitAddContainer();
        this.exitParticipantsContainer();
        this.loadAllEventsByCategoryId(selectedCategory.getId());
        this.refreshEventList(this.events);
    }

    @FXML
    private void clearFilterEvents() {
        this.loadAllEvents();
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.refreshEventList(this.events);
    }

    @FXML
    private void createAddContainer() {
        ViewHelpers.disableContainer(this.participantsContainer);
        ViewHelpers.showContainer(this.addContainer);
    }

    private void createParticipantsContainer() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }
        this.exitAddContainer();
        ViewHelpers.showContainer(this.participantsContainer);
        this.loadEventContacts(selectedEvent.getId());
        this.loadContactsToAdd(selectedEvent.getId(), selectedEvent.getCategoryId());
        ComboBoxHelpers.setupParticipantsComboBox(this.contactsToAdd, this.categories, this.participantsComboBox);
        this.refreshParticipantList(this.eventContacts);
    }

    @FXML
    private void exitAddContainer() {
        ViewHelpers.disableContainer(this.addContainer);
    }

    @FXML
    private void exitParticipantsContainer() {
        ViewHelpers.disableContainer(this.participantsContainer);
    }

    @FXML
    private void addEvent() {
        if (!EventValidation.validateForm(this.titleTextField, this.datePicker,
                this.categoryItemComboBox, this.responseLabel)) {
            return;
        }
        Event event = this.createEventFromForm();
        this.eventService.addEvent(event);
        this.resetForm();
        this.loadAllEvents();
        this.refreshEventList(this.events);
    }

    @FXML
    private void deleteEvent() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }
        eventService.deleteEventById(selectedEvent.getId());
        this.exitParticipantsContainer();
        this.loadAllEvents();
        this.refreshEventList(this.events);
    }

    @FXML
    private void addNewParticipant() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        Contact contact = this.participantsComboBox.getSelectionModel().getSelectedItem();
        if (contact == null || selectedEvent == null) {
            return;
        }
        this.eventService.addContactToEvent(selectedEvent.getId(), contact.getId());
        this.loadEventContacts(selectedEvent.getId());
        this.loadContactsToAdd(selectedEvent.getId(), contact.getId());
        ComboBoxHelpers.setupParticipantsComboBox(this.contactsToAdd, this.categories, this.participantsComboBox);
        this.refreshParticipantList(this.eventContacts);
    }

    private void loadAllCategories() {
        this.categories = this.categoryService.getAllCategories();
    }

    private void loadAllEvents() {
        this.events = this.eventService.getAllEvents();
    }

    private void loadAllEventsByCategoryId(int categoryId) {
        this.events = this.eventService.getAllByCategoryId(categoryId);
    }

    private void loadEventContacts(int eventId) {
        this.eventContacts = this.contactService.getAllContactsByEventId(eventId);
    }

    private void loadContactsToAdd(int eventId, int categoryId) {
        this.contactsToAdd = this.contactService.getAvailableContactsToAddToEvent(eventId, categoryId);
    }

    private void refreshEventList(List<Event> events) {
        this.eventListView.getItems().setAll(events);
        this.eventListView.setCellFactory(lv -> ListCellHelpers.eventWithCategoryContactList(this.categories));
    }

    private void refreshParticipantList(List<Contact> participants) {
        this.eventContactListView.getItems().setAll(participants);
        this.eventContactListView.setCellFactory(lv -> ListCellHelpers.contactWithCategoryContactList(this.categories));
    }

    private Event createEventFromForm() {
        Event event = new Event();
        event.setTitle(this.titleTextField.getText());
        event.setDate(this.datePicker.getValue());
        event.setCategoryId(this.categoryItemComboBox.getValue().getId());
        return event;
    }

    private void resetForm() {
        ViewHelpers.clearInputFields(this.titleTextField);
        ViewHelpers.clearResponseMessage(this.responseLabel);
        this.exitAddContainer();
    }
}
