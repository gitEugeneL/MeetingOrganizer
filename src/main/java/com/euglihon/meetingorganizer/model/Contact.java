package com.euglihon.meetingorganizer.model;

import java.util.List;

/**
 * Represents a contact model.
 */
public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private int categoryId;

    /**
     * Default constructor.
     * Initializes a new Contact object without setting any properties.
     */
    public Contact() {}

    /**
     * Constructor with parameters to initialize a Contact object with an id, first name, last name, phone, and category id.
     *
     * @param id        the unique identifier for the contact
     * @param firstName the first name of the contact
     * @param lastName  the last name of the contact
     * @param phone     the phone number of the contact
     * @param categoryId the id of the category to which the contact belongs
     */
    public Contact(int id, String firstName, String lastName, String phone, int categoryId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.categoryId = categoryId;
    }

    /**
     * Returns a string representation of the Contact object.
     * The format is: "firstName | lastName | phone"
     *
     * @return a string representing the contact's first name, last name, and phone number
     */
    @Override
    public String toString() {
        return this.firstName + " | " + this.lastName + " | " + this.phone;
    }

    /**
     * Gets the unique identifier of the contact.
     *
     * @return the id of the contact
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the contact.
     *
     * @param id the unique identifier to set for the contact
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the first name of the contact.
     *
     * @return the first name of the contact
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the contact.
     *
     * @param firstName the first name to set for the contact
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the contact.
     *
     * @return the last name of the contact
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the contact.
     *
     * @param lastName the last name to set for the contact
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number of the contact.
     *
     * @return the phone number of the contact
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param phone the phone number to set for the contact
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the category id of the contact.
     *
     * @return the category id of the contact
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category id for the contact.
     *
     * @param categoryId the category id to assign to the contact
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Returns the category associated with this contact.
     *
     * @param categories a list of categories to search for the contact's category
     * @return the category associated with this contact, or null if not found
     */
    public Category getCategory(List<Category> categories) {
        return categories.stream()
                .filter(category -> category.getId() == this.categoryId)
                .findFirst()
                .orElse(null);
    }
}
