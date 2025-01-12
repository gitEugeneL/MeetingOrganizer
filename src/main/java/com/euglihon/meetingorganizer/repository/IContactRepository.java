package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

public interface IContactRepository {

    void createTable();

    List<Contact> findAll();

    List<Contact> findAllByCategoryId(int categoryId);

    List<Contact> findAllByEventId(int eventId);

    void insert(Contact contact);

    void update(Contact contact);

    Contact findByPhone(String phone);

    List<Contact> findAvailableContactsToAddToEvent(int eventId, int categoryId);

    boolean isPhoneExist(Contact contact);

    boolean isContactAssociatedWithEvent(int contactId);

    Contact findById(int contactId);

    void deleteById(int contactId);
}
