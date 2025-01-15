package com.euglihon.meetingorganizer.repository;

import com.euglihon.meetingorganizer.model.Contact;

import java.util.List;

/**
 * This interface defines the methods for managing contact data in the repository.
 */
public interface IContactRepository {

    /**
     * Create the contacts table in the database.
     */
    void createTable();

    /**
     * Find all contacts in the database.
     *
     * @return a list of all contacts.
     */
    List<Contact> findAll();

    /**
     * Find all contacts by category ID.
     *
     * @param categoryId the ID of the category.
     * @return a list of contacts in the category.
     */
    List<Contact> findAllByCategoryId(int categoryId);

    /**
     * Find all contacts by event ID.
     *
     * @param eventId the ID of the event.
     * @return a list of contacts associated with the event.
     */
    List<Contact> findAllByEventId(int eventId);

    /**
     * Insert a new contact into the database.
     *
     * @param contact the contact to insert.
     */
    void insert(Contact contact);

    /**
     * Update an existing contact in the database.
     *
     * @param contact the contact to update.
     */
    void update(Contact contact);

    /**
     * Find a contact by phone number.
     *
     * @param phone the phone number of the contact.
     * @return the contact with the specified phone number, or null if not found.
     */
    Contact findByPhone(String phone);

    /**
     * Find available contacts to add to an event by category ID.
     *
     * @param eventId the ID of the event.
     * @param categoryId the ID of the category.
     * @return a list of available contacts for the event and category.
     */
    List<Contact> findAvailableContactsToAddToEvent(int eventId, int categoryId);

    /**
     * Check if a contact's phone number exists in the database.
     *
     * @param contact the contact to check.
     * @return true if the phone number exists, false otherwise.
     */
    boolean isPhoneExist(Contact contact);

    /**
     * Check if a contact is associated with an event.
     *
     * @param contactId the ID of the contact.
     * @return true if the contact is associated with an event, false otherwise.
     */
    boolean isContactAssociatedWithEvent(int contactId);

    /**
     * Find a contact by its ID.
     *
     * @param contactId the ID of the contact.
     * @return the contact with the specified ID, or null if not found.
     */
    Contact findById(int contactId);

    /**
     * Delete a contact by its ID.
     *
     * @param contactId the ID of the contact to delete.
     */
    void deleteById(int contactId);
}
