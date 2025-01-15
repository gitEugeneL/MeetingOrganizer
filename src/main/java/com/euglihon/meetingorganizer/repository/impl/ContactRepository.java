package com.euglihon.meetingorganizer.repository.impl;

import com.euglihon.meetingorganizer.data.DbContext;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.repository.IContactRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class implements the IContactRepository interface.
 * It provides methods for managing contacts in the database.
 */
public class ContactRepository implements IContactRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final DbContext dbContext;

    /**
     * Constructor that initializes the ContactRepository with a DbContext.
     *
     * @param dbContext The database context for database operations.
     */
    public ContactRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Maps a ResultSet row to a Contact object.
     * This method is used to transform the data from the ResultSet into a Contact.
     *
     * @param resultSet The ResultSet to map from.
     * @return A Contact object.
     * @throws SQLException If there is an error accessing the ResultSet.
     */
    private Contact mapToContact(ResultSet resultSet) throws SQLException {
        return new Contact(
                resultSet.getInt("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("phone"),
                resultSet.getInt("categoryId")
        );
    }

    /**
     * Creates the Contacts table in the database if it does not exist.
     * This method ensures the table structure is set up before any data operations.
     */
    @Override
    public void createTable() {
        dbContext.getConnection();
        String query = """
                CREATE TABLE IF NOT EXISTS Contacts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    firstName VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    phone Varchar(15) NOT NULL UNIQUE,
                    categoryId INTEGER NOT NULL,
                    FOREIGN KEY(categoryId) REFERENCES Categories(id) ON DELETE CASCADE ON UPDATE CASCADE);
                """;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.execute();
        } catch (SQLException ignored) {
            logger.severe("Failed to create Contacts table");
        }
    }

    /**
     * Finds and returns all contacts in the Contacts table.
     *
     * @return A list of all contacts.
     */
    @Override
    public List<Contact> findAll() {
        dbContext.getConnection();
        String query = "SELECT id, firstName, lastName, phone, categoryId from Contacts;";
        List<Contact> contacts = new ArrayList<>();
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Map each result row to a Contact and add to the list
                contacts.add(this.mapToContact(resultSet));
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to find all Contacts");
        }
        // Return the list of contacts
        return contacts;
    }

    /**
     * Finds and returns all contacts linked with the event.
     *
     * @param eventId The ID of the event.
     * @return A list of contacts associated with the event.
     */
    @Override
    public List<Contact> findAllByEventId(int eventId) {
        dbContext.getConnection();
        String query = """
                    SELECT c.id, c.firstName, c.lastName, c.phone, c.categoryId
                    FROM Contacts c
                    INNER JOIN Events_Contacts ec ON c.id = ec.contactId
                    WHERE ec.eventId = ?;
                """;
        List<Contact> contacts = new ArrayList<>();
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Map each result row to a Contact and add to the list
                contacts.add(this.mapToContact(resultSet));
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to find all Contacts for event");
        }
        // Return the list of contacts
        return contacts;
    }

    /**
     * Finds and returns contacts that are available to be added to a specific event and category.
     *
     * @param eventId   The ID of the event.
     * @param categoryId The ID of the category.
     * @return A list of available contacts for the event and category.
     */
    @Override
    public List<Contact> findAvailableContactsToAddToEvent(int eventId, int categoryId) {
        dbContext.getConnection();
        String query = """
                    SELECT c.id, c.firstName, c.lastName, c.phone, c.categoryId
                    FROM Contacts c
                    LEFT JOIN Events_Contacts ec ON c.id = ec.contactId AND ec.eventId = ?
                    WHERE ec.eventId IS NULL AND c.categoryId = ?;
                """;
        List<Contact> contacts = new ArrayList<>();
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, eventId);
            statement.setInt(2, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Map each result row to a Contact and add to the list
                contacts.add(this.mapToContact(resultSet));
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to find available Contacts to add to Event");
        }
        // Return the list of available contacts
        return contacts;
    }

    /**
     * Finds and returns all contacts associated with a specific category.
     *
     * @param categoryId The ID of the category.
     * @return A list of contacts in the specified category.
     */
    @Override
    public List<Contact> findAllByCategoryId(int categoryId) {
        dbContext.getConnection();
        String query = "SELECT id, firstName, lastName, phone, categoryId FROM Contacts WHERE categoryId = ?;";
        List<Contact> contacts = new ArrayList<>();

        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Map each result row to a Contact and add to the list
                contacts.add(this.mapToContact(resultSet));
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to find all Contacts by Category Id");
        }
        // Return the list of contacts
        return contacts;
    }

    /**
     * Inserts a new contact into the Contacts table.
     *
     * @param contact The contact to insert.
     */
    @Override
    public void insert(Contact contact) {
        dbContext.getConnection();
        String query = "INSERT INTO Contacts (firstName, lastName, phone, categoryId) VALUES (?, ?, ?, ?);";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setInt(4, contact.getCategoryId());
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to insert new Contact");
        }
    }

    /**
     * Updates an existing contact in the Contacts table.
     *
     * @param contact The contact to update.
     */
    @Override
    public void update(Contact contact) {
        dbContext.getConnection();
        String query = """
                UPDATE Contacts SET
                    firstName = ?, lastName = ?, phone = ?, categoryId = ?
                    WHERE id = ?;
                """;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setInt(4, contact.getCategoryId());
            statement.setInt(5, contact.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Failed to update Contact");
        }
    }

    /**
     * Finds a contact by its phone number.
     *
     * @param phone The phone number of the contact to find.
     * @return The contact with the specified phone number, or null if not found.
     */
    @Override
    public Contact findByPhone(String phone) {
        dbContext.getConnection();
        String query = "SELECT id, firstName, lastName, phone, categoryId FROM Contacts WHERE phone = ?;";
        Contact contact = null;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                contact = this.mapToContact(resultSet);
            }
        } catch (SQLException e) {
            logger.severe("Failed to find Contact by Phone");
        }
        // Return the contact or null if not found
        return contact;
    }

    /**
     * Checks if a contact's phone number already exists in the database.
     *
     * @param contact The contact to check.
     * @return true if the phone number exists, false otherwise.
     */
    @Override
    public boolean isPhoneExist(Contact contact) {
        dbContext.getConnection();
        boolean exists = false;
        String query = "SELECT id FROM Contacts WHERE phone = ? AND id != ?;";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, contact.getPhone());
            statement.setInt(2, contact.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            logger.severe("Failed to find Contact by Phone");
        }
        return exists;
    }

    /**
     * Finds a contact by its ID.
     *
     * @param contactId The ID of the contact to find.
     * @return The contact with the specified ID, or null if not found.
     */
    @Override
    public Contact findById(int contactId) {
        dbContext.getConnection();
        String query = "SELECT id, firstName, lastName, phone, categoryId FROM Contacts WHERE id = ?;";
        Contact contact = null;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, contactId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                contact = this.mapToContact(resultSet);
            }

        } catch (SQLException e) {
            logger.severe("Failed to find Contact by Id");
        }
        return contact;
    }

    /**
     * Checks if a contact is associated with the event.
     *
     * @param contactId The ID of the contact to check.
     * @return true if the contact is associated with an event, false otherwise.
     */
    @Override
    public boolean isContactAssociatedWithEvent(int contactId) {
        dbContext.getConnection();
        String query = "SELECT COUNT(*) AS count FROM Events_Contacts ec WHERE ec.contactId = ?;";
        boolean isAssociated = false;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, contactId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                isAssociated = count > 0;
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to find Contact Associated with Event");
        }
        return isAssociated;
    }

    /**
     * Deletes a contact from the Contacts table by  contact ID.
     *
     * @param contactId The ID of the contact to delete.
     */
    @Override
    public void deleteById(int contactId) {
        dbContext.getConnection();
        String query = "DELETE FROM Contacts WHERE id = ?;";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, contactId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Failed to delete Contact by Id");
        }
    }
}
