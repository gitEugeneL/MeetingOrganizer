package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Event;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface helps to manage events.
 */
public interface IEventService {

    /**
     * Add a new event.
     *
     * @param event the event to add.
     */
    void addEvent(Event event);

    /**
     * Update an event.
     *
     * @param event the event to update.
     * @return true if the event was updated, false if not.
     */
    boolean updateEvent(Event event);

    /**
     * Add a contact to an event.
     *
     * @param eventId the ID of the event.
     * @param contactId the ID of the contact.
     */
    void addContactToEvent(int eventId, int contactId);

    /**
     * Remove a contact from an event.
     *
     * @param eventId the ID of the event.
     * @param contactId the ID of the contact.
     */
    void removeContactFromEvent(int eventId, int contactId);

    /**
     * Get all events.
     *
     * @return a list of all events.
     */
    List<Event> getAllEvents();

    /**
     * Get all events by category ID.
     *
     * @param categoryId the ID of the category.
     * @return a list of events in the category.
     */
    List<Event> getAllByCategoryId(int categoryId);

    /**
     * Delete an event by ID.
     *
     * @param eventId the ID of the event to delete.
     */
    void deleteEventById(int eventId);

    /**
     * Delete events older than a certain date.
     *
     * @param date the date to compare.
     */
    void deleteEventOlderThan(LocalDate date);
}
