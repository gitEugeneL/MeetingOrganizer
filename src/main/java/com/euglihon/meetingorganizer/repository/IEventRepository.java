package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Event;

import java.util.List;

public interface IEventRepository {

    void createTable();

    void insert(Event event);

    List<Event> findAll(Integer eventId);

    List<Event> findAllByCategoryId(int categoryId);

    void deleteById(int eventId);

    // todo delete by date
}
