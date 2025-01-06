package com.euglihon.meetingorganizer.service.impl;

import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.repository.IContactRepository;
import com.euglihon.meetingorganizer.service.IContactService;

import java.util.List;

public class ContactService implements IContactService {

    private final IContactRepository contactRepository;
    public ContactService(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return this.contactRepository.findAll();
    }

    @Override
    public boolean addContact(Contact contact) {
        if (this.contactRepository.findByPhone(contact.getPhone()) != null) {
            return false;
        }
        this.contactRepository.insert(contact);
        return true;
    }

    @Override
    public boolean updateContact(Contact contact) {
        if (this.contactRepository.findById(contact.getId()) == null) {
            return false;
        }
        if (this.contactRepository.isPhoneExist(contact)) {
            return false;
        }
        this.contactRepository.update(contact);
        return true;
    }

    @Override
    public void deleteContactById(int contactId) {
        this.contactRepository.deleteById(contactId);
    }
}
