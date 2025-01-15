package com.euglihon.meetingorganizer.service.impl;

import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.repository.IEventRepository;
import com.euglihon.meetingorganizer.service.IEventService;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the IEventService interface for managing events.
 */
public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    /**
     * Constructor to initialize the EventService with a repository.
     *
     * @param eventRepository the repository used for event operations.
     */
    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Add a new event.
     *
     * @param event the event to add.
     */
    @Override
    public void addEvent(Event event) {
        this.eventRepository.insert(event);
    }

    /**
     * Update an existing event.
     *
     * @param event the event to update.
     * @return true if the event was updated, false if the event does not exist.
     */
    @Override
    public boolean updateEvent(Event event) {
        // Check if the event exists
        if (!this.eventRepository.isEventExist(event)) {
            return false;
        }
        // Update the event in the repository
        this.eventRepository.update(event);
        return true;
    }

    /**
     * Add a contact to an event.
     *
     * @param eventId the ID of the event.
     * @param contactId the ID of the contact to add.
     */
    @Override
    public void addContactToEvent(int eventId, int contactId) {
        this.eventRepository.addContact(eventId, contactId);
    }

    /**
     * Exclude a contact from an event.
     *
     * @param eventId the ID of the event.
     * @param contactId the ID of the contact to remove.
     */
    @Override
    public void removeContactFromEvent(int eventId, int contactId) {
        this.eventRepository.removeContact(eventId, contactId);
    }

    /**
     * Get all events.
     *
     * @return a list of all events.
     */
    @Override
    public List<Event> getAllEvents() {
        return this.eventRepository.findAll(null);
    }

    /**
     * Get all events by category ID.
     *
     * @param eventId the ID of the category.
     * @return a list of events in the category.
     */
    @Override
    public List<Event> getAllByCategoryId(int eventId) {
        return this.eventRepository.findAllByCategoryId(eventId);
    }

    /**
     * Delete an event by its ID.
     *
     * @param eventId the ID of the event to delete.
     */
    @Override
    public void deleteEventById(int eventId) {
        this.eventRepository.deleteById(eventId);
    }

    /**
     * Delete events older than a specific date.
     *
     * @param date the date before which events should be deleted.
     */
    @Override
    public void deleteEventOlderThan(LocalDate date) {
        this.eventRepository.deleteOlderThan(date);
    }
}
