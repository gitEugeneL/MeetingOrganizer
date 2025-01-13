package com.euglihon.meetingorganizer.service.impl;

import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.repository.IEventRepository;
import com.euglihon.meetingorganizer.service.IEventService;

import java.time.LocalDate;
import java.util.List;

public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void addEvent(Event event) {
        this.eventRepository.insert(event);
    }

    @Override
    public boolean updateEvent(Event event) {
        if (!this.eventRepository.isEventExist(event)) {
            return false;
        }
        this.eventRepository.update(event);
        return true;
    }

    @Override
    public void addContactToEvent(int eventId, int contactId) {
        this.eventRepository.addContact(eventId, contactId);
    }

    @Override
    public void removeContactFromEvent(int eventId, int contactId) {
        this.eventRepository.removeContact(eventId, contactId);
    }

    @Override
    public List<Event> getAllEvents() {
        return this.eventRepository.findAll(null);
    }

    @Override
    public List<Event> getAllByCategoryId(int eventId) {
        return this.eventRepository.findAllByCategoryId(eventId);
    }

    @Override
    public void deleteEventById(int eventId) {
        this.eventRepository.deleteById(eventId);
    }

    @Override
    public void deleteEventOlderThan(LocalDate date) {
        this.eventRepository.deleteOlderThan(date);
    }
}
