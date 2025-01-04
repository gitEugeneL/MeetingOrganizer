package com.euglihon.meetingorganizer.data;

import com.euglihon.meetingorganizer.repository.ICategoryRepository;
import com.euglihon.meetingorganizer.repository.IContactRepository;

public class DbInitialization {

    private final ICategoryRepository categoryRepository;
    private final IContactRepository contactRepository;
    public DbInitialization(ICategoryRepository categoryRepository, IContactRepository contactRepository) {
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
    }

    public void init() {
        categoryRepository.createTable();
        contactRepository.createTable();
    }
}
