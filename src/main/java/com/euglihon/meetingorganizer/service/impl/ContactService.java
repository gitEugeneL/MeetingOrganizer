package com.euglihon.meetingorganizer.service.impl;

import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.repository.IContactRepository;
import com.euglihon.meetingorganizer.service.IContactService;

import java.util.List;

/**
 * Implementation of the IContactService interface for managing contacts.
 */
public class ContactService implements IContactService {

    private final IContactRepository contactRepository;

    /**
     * Constructor to initialize the ContactService with a repository.
     *
     * @param contactRepository the repository used for contact operations.
     */
    public ContactService(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Get all contacts.
     *
     * @return a list of all contacts.
     */
    @Override
    public List<Contact> getAllContacts() {
        return this.contactRepository.findAll();
    }

    /**
     * Get all contacts by category ID.
     *
     * @param categoryId the ID of the category.
     * @return a list of contacts in the category.
     */
    @Override
    public List<Contact> getAllContactsByCategoryId(int categoryId) {
        return this.contactRepository.findAllByCategoryId(categoryId);
    }

    /**
     * Get all contacts by event ID.
     *
     * @param eventId the ID of the event.
     * @return a list of contacts for the event.
     */
    @Override
    public List<Contact> getAllContactsByEventId(int eventId) {
        return this.contactRepository.findAllByEventId(eventId);
    }

    /**
     * Get contacts that can be added to an event by category ID.
     *
     * @param eventId the ID of the event.
     * @param categoryId the ID of the category.
     * @return a list of contacts that can be added to the event.
     */
    @Override
    public List<Contact> getAvailableContactsToAddToEvent(int eventId, int categoryId) {
        return this.contactRepository.findAvailableContactsToAddToEvent(eventId, categoryId);
    }

    /**
     * Add a new contact if it does not already exist by phone number.
     *
     * @param contact the contact to add.
     * @return true if the contact was added, false if it already exists.
     */
    @Override
    public boolean addContact(Contact contact) {
        // Check if a contact with the same phone number already exists
        if (this.contactRepository.findByPhone(contact.getPhone()) != null) {
            return false;
        }
        // Insert the new contact into the repository
        this.contactRepository.insert(contact);
        return true;
    }

    /**
     * Check if a contact is associated with an event.
     *
     * @param contactId the ID of the contact.
     * @return true if the contact is associated with the event, false if not.
     */
    @Override
    public boolean isContactAssociatedWithEvent(int contactId) {
        return this.contactRepository.isContactAssociatedWithEvent(contactId);
    }

    /**
     * Update a contact if it exists and the phone number is not used by another contact.
     *
     * @param contact the contact to update.
     * @return true if the contact was updated, false if it doesn't exist or the phone number is used.
     */
    @Override
    public boolean updateContact(Contact contact) {
        // Check if the contact exists
        if (this.contactRepository.findById(contact.getId()) == null) {
            return false;
        }
        // Check if the phone number is used by another contact
        if (this.contactRepository.isPhoneExist(contact)) {
            return false;
        }
        // Update the contact in the repository
        this.contactRepository.update(contact);
        return true;
    }

    /**
     * Delete a contact by its ID.
     *
     * @param contactId the ID of the contact to delete.
     */
    @Override
    public void deleteContactById(int contactId) {
        this.contactRepository.deleteById(contactId);
    }
}
