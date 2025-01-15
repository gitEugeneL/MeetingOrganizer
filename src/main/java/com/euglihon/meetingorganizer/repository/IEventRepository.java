package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Event;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface defines the methods for managing event data in the repository.
 */
public interface IEventRepository {

    /**
     * Create the events table in the database.
     */
    void createTable();

    /**
     * Insert a new event into the database.
     *
     * @param event the event to insert.
     */
    void insert(Event event);

    /**
     * Update an existing event in the database.
     *
     * @param event the event to update.
     */
    void update(Event event);

    /**
     * Add a contact to an event.
     *
     * @param eventId the ID of the event.
     * @param contactId the ID of the contact to add.
     */
    void addContact(int eventId, int contactId);

    /**
     * Remove a contact from an event.
     *
     * @param eventId the ID of the event.
     * @param contactId the ID of the contact to remove.
     */
    void removeContact(int eventId, int contactId);

    /**
     * Check if an event exists in the database.
     *
     * @param event the event to check.
     * @return true if the event exists, false otherwise.
     */
    boolean isEventExist(Event event);

    /**
     * Find all events, optionally filtered by event ID.
     *
     * @param eventId the ID of the event to filter by, or null to retrieve all events.
     * @return a list of events.
     */
    List<Event> findAll(Integer eventId);

    /**
     * Find all events by category ID.
     *
     * @param categoryId the ID of the category.
     * @return a list of events in the category.
     */
    List<Event> findAllByCategoryId(int categoryId);

    /**
     * Delete an event by its ID.
     *
     * @param eventId the ID of the event to delete.
     */
    void deleteById(int eventId);

    /**
     * Delete events older than a specific date.
     *
     * @param date the date before which events should be deleted.
     */
    void deleteOlderThan(LocalDate date);
}
