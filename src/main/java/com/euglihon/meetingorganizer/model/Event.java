package com.euglihon.meetingorganizer.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an event model.
 */
public class Event {
    private int id;
    private String title;
    private LocalDate date;
    private List<Contact> participants;
    private int categoryId;

    /**
     * Default constructor.
     * Initializes a new Event object without setting any properties.
     */
    public Event() {}


    /**
     * Constructor with parameters to initialize an Event object with an id, title, date, participants, and category id.
     *
     * @param id          the unique identifier for the event
     * @param title       the title of the event
     * @param date        the date when the event takes place
     * @param participants the list of participants in the event
     * @param categoryId  the id of the category to which the event belongs
     */
    public Event(int id, String title, LocalDate date, List<Contact> participants, int categoryId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.participants = participants;
        this.categoryId = categoryId;
    }

    /**
     * Gets the unique identifier of the event.
     *
     * @return the id of the event
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the event.
     *
     * @param id the unique identifier to set for the event
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the event.
     *
     * @return the title of the event
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the event.
     *
     * @param title the title to set for the event
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the date of the event.
     *
     * @return the date of the event
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the event.
     *
     * @param date the date to set for the event
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the list of participants for the event.
     *
     * @return a list of participants in the event
     */
    public List<Contact> getParticipants() {
        return participants;
    }

    /**
     * Sets the list of participants for the event.
     *
     * @param participants the list of participants to set for the event
     */
    public void setParticipants(List<Contact> participants) {
        this.participants = participants;
    }

    /**
     * Gets the category id of the event.
     *
     * @return the category id of the event
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category id for the event.
     *
     * @param categoryId the category id to assign to the event
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Returns the category associated with this event.
     *
     * @param categories a list of categories to search for the event's category
     * @return the category associated with this event, or null if not found
     */
    public Category getCategory(List<Category> categories) {
        return categories.stream()
                .filter(category -> category.getId() == this.categoryId)
                .findFirst()
                .orElse(null);
    }
}
