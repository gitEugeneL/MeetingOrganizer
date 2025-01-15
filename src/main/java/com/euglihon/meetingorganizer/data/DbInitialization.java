package com.euglihon.meetingorganizer.data;

import com.euglihon.meetingorganizer.repository.ICategoryRepository;
import com.euglihon.meetingorganizer.repository.IContactRepository;
import com.euglihon.meetingorganizer.repository.IEventRepository;

/**
 * Class for initializing the database tables for categories, contacts, and events.
 * It creates the necessary tables by interacting with the repositories.
 */
public class DbInitialization {

    private final ICategoryRepository categoryRepository;
    private final IContactRepository contactRepository;
    private final IEventRepository eventRepository;

    /**
     * Constructs a new DbInitialization object with the specified repositories.
     *
     * @param categoryRepository the repository for managing categories
     * @param contactRepository the repository for managing contacts
     * @param eventRepository the repository for managing events
     */
    public DbInitialization(
            ICategoryRepository categoryRepository,
            IContactRepository contactRepository,
            IEventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Initializes the database by creating the necessary tables for categories, contacts, and events.
     * It calls the createTable method for each repository.
     */
    public void init() {
        categoryRepository.createTable();
        contactRepository.createTable();
        eventRepository.createTable();
    }
}
