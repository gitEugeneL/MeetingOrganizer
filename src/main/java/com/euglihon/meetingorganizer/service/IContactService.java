package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

public interface IContactService {

    List<Contact> getAllContacts();

    boolean addContact(Contact contact);
}
