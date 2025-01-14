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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class EventController {

    private final LocalDate AUTO_DELETE_DATE = LocalDate.of(2020, 12, 31);

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
    private VBox addContainer, participantsContainer, editContainer;
    @FXML
    private HBox excludeParticipantContainer;
    @FXML
    private TextField titleTextField, updateTitle;
    @FXML
    private DatePicker createEventPicker, updateEventPicker, deleteOlderEvenPicker;
    @FXML
    private Label responseLabel;

    @FXML
    private void initialize() {
        this.deleteByDate(AUTO_DELETE_DATE);
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
    void participantListForm() {
        Contact selectedEventContact = this.eventContactListView.getSelectionModel().getSelectedItem();
        if (selectedEventContact == null) {
            return;
        }
        ViewHelpers.showContainer(this.excludeParticipantContainer);
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
        ViewHelpers.disableContainer(this.editContainer);
        ViewHelpers.showContainer(this.addContainer);
    }

    private void createParticipantsContainer() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }
        this.exitAddContainer();
        this.exitEditContainer();
        ViewHelpers.showContainer(this.participantsContainer);
        this.loadEventContacts(selectedEvent.getId());
        this.loadContactsToAdd(selectedEvent.getId(), selectedEvent.getCategoryId());
        ComboBoxHelpers.setupParticipantsComboBox(this.contactsToAdd, this.categories, this.participantsComboBox);
        this.refreshParticipantList(this.eventContacts);
    }

    @FXML
    private void createEditEventContainer() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }
        this.exitAddContainer();
        this.exitParticipantsContainer();
        ViewHelpers.showContainer(this.editContainer);
        this.updateTitle.setText(selectedEvent.getTitle());
        this.updateEventPicker.setValue(selectedEvent.getDate());
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
    private void exitEditContainer() {
        ViewHelpers.disableContainer(this.editContainer);
        ViewHelpers.disableContainer(this.excludeParticipantContainer);
    }

    private void finalizeEditing() {
        this.resetForm();
        this.exitAddContainer();
        this.exitParticipantsContainer();
        this.exitEditContainer();
        this.loadAllEvents();
        this.refreshEventList(this.events);
        this.clearFilterEvents();
    }

    @FXML
    private void addEvent() {
        if (!EventValidation.validateForm(this.titleTextField, this.createEventPicker,
                this.categoryItemComboBox, this.responseLabel)) {
            return;
        }
        Event event = new Event();
        event.setTitle(this.titleTextField.getText());
        event.setDate(this.createEventPicker.getValue());
        event.setCategoryId(this.categoryItemComboBox.getValue().getId());
        this.eventService.addEvent(event);
        this.finalizeEditing();
    }

    @FXML
    private void updateEvent() {
        if (!EventValidation.validateForm(this.updateTitle, this.updateEventPicker, this.responseLabel)) {
            return;
        }
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }
        selectedEvent.setTitle(this.updateTitle.getText());
        selectedEvent.setDate(this.updateEventPicker.getValue());

        if (!this.eventService.updateEvent(selectedEvent)) {
            ViewHelpers.createResponseMessage(this.responseLabel, "Event not updated");
        } else {
            this.finalizeEditing();
        }
    }

    @FXML
    private void deleteEvent() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            return;
        }
        eventService.deleteEventById(selectedEvent.getId());
        this.finalizeEditing();
    }

    @FXML
    private void deleteOlderEvents() {
        if (!EventValidation.validateForm(this.deleteOlderEvenPicker, this.responseLabel)) {
            return;
        }
        this.deleteByDate(this.deleteOlderEvenPicker.getValue());
        this.finalizeEditing();
    }

    private void deleteByDate(LocalDate date) {
        this.eventService.deleteEventOlderThan(date);
    }

    @FXML
    private void addNewParticipant() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        Contact contact = this.participantsComboBox.getSelectionModel().getSelectedItem();
        if (contact == null || selectedEvent == null) {
            return;
        }
        this.eventService.addContactToEvent(selectedEvent.getId(), contact.getId());
        updateParticipantList(selectedEvent, contact);
    }

    @FXML
    private void excludeParticipant() {
        Event selectedEvent = this.eventListView.getSelectionModel().getSelectedItem();
        Contact contact = this.eventContactListView.getSelectionModel().getSelectedItem();
        if (contact == null || selectedEvent == null) {
            return;
        }
        this.eventService.removeContactFromEvent(selectedEvent.getId(), contact.getId());
        updateParticipantList(selectedEvent, contact);
    }

    private void updateParticipantList(Event event, Contact contact) {
        this.loadEventContacts(event.getId());
        this.loadContactsToAdd(event.getId(), contact.getCategoryId());
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

    private void resetForm() {
        ViewHelpers.clearInputFields(this.updateTitle);
        ViewHelpers.clearInputFields(this.titleTextField);
        ViewHelpers.clearResponseMessage(this.responseLabel);
    }
}
