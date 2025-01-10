package com.euglihon.meetingorganizer.controller;

import com.euglihon.meetingorganizer.controller.common.Base;
import com.euglihon.meetingorganizer.helpers.ViewHelpers;
import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.IEventService;
import com.euglihon.meetingorganizer.validation.EventValidation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;

public class EventController {

    private final IEventService eventService;
    private final ICategoryService categoryService;
    public EventController(IEventService eventService, ICategoryService categoryService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
    }

    private List<Category> categories;
    private List<Event> events;

    @FXML private ListView<Event> eventListView;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private ComboBox<Category> categoryItemComboBox;
    @FXML private VBox addContainer, participantsContainer;
    @FXML private TextField titleTextField;
    @FXML private DatePicker datePicker;
    @FXML private Label responseLabel;

    @FXML private void initialize() {
        this.loadAllCategories();
        this.loadAllEvents();
        this.refreshEventList(this.events);
        Base.setupCategoryItemComboBox(this.categories, this.categoryComboBox);
        Base.setupCategoryComboBox(this.categories, this.categoryItemComboBox);
        this.clearFilterEvents();
    }

    @FXML private void eventListForm() {
        this.exitAddContainer();
        this.participantsContainer.setManaged(true);
        this.participantsContainer.setVisible(true);
    }

    @FXML private void filterEvents() {
        Category selectedCategory = this.categoryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        this.loadAllEventsByCategoryId(selectedCategory.getId());
        this.refreshEventList(this.events);
    }

    @FXML private void clearFilterEvents() {
        this.loadAllEvents();
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.refreshEventList(this.events);
    }

    @FXML private void createAddContainer() {
        this.participantsContainer.setManaged(false);
        this.participantsContainer.setVisible(false);
        this.addContainer.setManaged(true);
        this.addContainer.setVisible(true);
    }

    @FXML private void exitAddContainer() {
        this.addContainer.setManaged(false);
        this.addContainer.setVisible(false);
    }

    @FXML private void addEvent() {
        if (this.validateForm()) {
            return;
        }
        Event event = this.createEventFromForm();
        this.eventService.addEvent(event);
        this.resetForm();
        this.loadAllEvents();
        this.refreshEventList(this.events);
    }

    @FXML private void deleteEvent() {
        int eventId = this.eventListView.getSelectionModel().getSelectedItem().getId();
        eventService.deleteEventById(eventId);
        this.loadAllEvents();
        this.refreshEventList(this.events);
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

    private void refreshEventList(List<Event> events) {
        this.eventListView.getItems().setAll(events);
        this.eventListView.setCellFactory(lv -> ViewHelpers.EventWithCategoryContactList(this.categories));
    }

    private Event createEventFromForm() {
        Event event = new Event();
        event.setTitle(this.titleTextField.getText());
        event.setDate(this.datePicker.getValue());
        event.setCategoryId(this.categoryItemComboBox.getValue().getId());
        return event;
    }

    private void resetForm() {
        ViewHelpers.ClearInputFields(this.titleTextField);
        ViewHelpers.ClearResponseMessage(this.responseLabel);
        this.exitAddContainer();
    }

    private boolean validateForm() {
        return !EventValidation.validateFields(
                this.titleTextField,
                this.datePicker,
                this.categoryItemComboBox,
                this.responseLabel);
    }
}
