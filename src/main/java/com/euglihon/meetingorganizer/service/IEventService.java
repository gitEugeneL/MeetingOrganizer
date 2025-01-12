package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Event;

import java.util.List;

public interface IEventService {

    void addEvent(Event event);

    void addContactToEvent(int eventId, int contactId);

    List<Event> getAllEvents();

    List<Event> getAllByCategoryId(int categoryId);

    void deleteEventById(int eventId);
}
