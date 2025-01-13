package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface IEventService {

    void addEvent(Event event);

    boolean updateEvent(Event event);

    void addContactToEvent(int eventId, int contactId);

    void removeContactFromEvent(int eventId, int contactId);

    List<Event> getAllEvents();

    List<Event> getAllByCategoryId(int categoryId);

    void deleteEventById(int eventId);

    void deleteEventOlderThan(LocalDate date);
}
