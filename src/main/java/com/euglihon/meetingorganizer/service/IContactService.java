package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

public interface IContactService {

    List<Contact> getAllContacts();

    List<Contact> getAllContactsByCategoryId(int categoryId);

    boolean addContact(Contact contact);

    boolean updateContact(Contact contact);

    void deleteContactById(int contactId);
}
