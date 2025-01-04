package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

public interface IContactRepository {

    void createTable();

    List<Contact> findAll();

    void insert(Contact contact);

    Contact findContactByPhone(String phone);
}
