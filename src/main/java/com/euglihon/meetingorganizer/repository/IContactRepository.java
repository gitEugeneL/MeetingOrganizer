package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

public interface IContactRepository {

    void createTable();

    List<Contact> findAll();

    List<Contact> findAllByCategoryId(int categoryId);

    void insert(Contact contact);

    void update(Contact contact);

    Contact findByPhone(String phone);

    boolean isPhoneExist(Contact contact);

    Contact findById(int contactId);

    void deleteById(int contactId);
}
