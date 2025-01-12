package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

public interface IContactService {

    List<Contact> getAllContacts();

    List<Contact> getAllContactsByCategoryId(int categoryId);

    List<Contact> getAllContactsByEventId(int eventId);

    List<Contact> getAvailableContactsToAddToEvent(int eventId, int categoryId);

    boolean addContact(Contact contact);

    boolean updateContact(Contact contact);

    void deleteContactById(int contactId);

    boolean isContactAssociatedWithEvent(int contactId);
}
