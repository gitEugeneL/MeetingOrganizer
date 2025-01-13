package com.euglihon.meetingorganizer.data;

import com.euglihon.meetingorganizer.repository.ICategoryRepository;
import com.euglihon.meetingorganizer.repository.IContactRepository;
import com.euglihon.meetingorganizer.repository.IEventRepository;

public class DbInitialization {

    private final ICategoryRepository categoryRepository;
    private final IContactRepository contactRepository;
    private final IEventRepository eventRepository;

    public DbInitialization(
            ICategoryRepository categoryRepository,
            IContactRepository contactRepository,
            IEventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
        this.eventRepository = eventRepository;
    }

    public void init() {
        categoryRepository.createTable();
        contactRepository.createTable();
        eventRepository.createTable();
    }
}
