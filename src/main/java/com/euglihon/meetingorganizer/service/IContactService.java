package com.euglihon.meetingorganizer.service;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

/**
 * Service interface for managing contacts in the meeting organizer application.
 */
public interface IContactService {

    /**
     * Get all contacts.
     *
     * @return a list of all contacts.
     */
    List<Contact> getAllContacts();

    /**
     * Get all contacts by category ID.
     *
     * @param categoryId the ID of the category.
     * @return a list of contacts in the category.
     */
    List<Contact> getAllContactsByCategoryId(int categoryId);

    /**
     * Get all contacts by event ID.
     *
     * @param eventId the ID of the event.
     * @return a list of contacts for the event.
     */
    List<Contact> getAllContactsByEventId(int eventId);

    /**
     * Get contacts that can be added to an event by category ID.
     *
     * @param eventId the ID of the event.
     * @param categoryId the ID of the category.
     * @return a list of contacts that can be added to the event.
     */
    List<Contact> getAvailableContactsToAddToEvent(int eventId, int categoryId);

    /**
     * Add a new contact.
     *
     * @param contact the contact to add.
     * @return true if the contact was added, false if not.
     */
    boolean addContact(Contact contact);

    /**
     * Update a contact.
     *
     * @param contact the contact to update.
     * @return true if the contact was updated, false if not.
     */
    boolean updateContact(Contact contact);

    /**
     * Delete a contact by ID.
     *
     * @param contactId the ID of the contact to delete.
     */
    void deleteContactById(int contactId);

    /**
     * Check if a contact is linked to an event.
     *
     * @param contactId the ID of the contact.
     * @return true if the contact is linked to the event, false if not.
     */
    boolean isContactAssociatedWithEvent(int contactId);
}
