package com.euglihon.meetingorganizer.repository.impl;

import com.euglihon.meetingorganizer.data.DbContext;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.repository.IEventRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class implements the IEventRepository interface.
 * It provides methods for managing events in the database.
 */
public class EventRepository implements IEventRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final DbContext dbContext;

    /**
     * Constructor to initialize EventRepository with DbContext.
     *
     * @param dbContext The DbContext object for database interaction.
     */
    public EventRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Creates the Events and Events_Contacts (MTM relation) tables if they do not already exist.
     */
    @Override
    public void createTable() {
        dbContext.getConnection();
        String query1 = """
                CREATE TABLE IF NOT EXISTS Events (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title VARCHAR(255) NOT NULL,
                    date DATE NOT NULL,
                    categoryId INTEGER NOT NULL,
                    FOREIGN KEY(categoryId) REFERENCES Categories(id) ON DELETE CASCADE ON UPDATE CASCADE);""";

        String query2 = """
                CREATE TABLE IF NOT EXISTS Events_Contacts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    eventId INTEGER NOT NULL,
                    contactId INTEGER NOT NULL,
                    FOREIGN KEY (eventId) REFERENCES Events(id) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY (contactId) REFERENCES Contacts(id) ON DELETE CASCADE ON UPDATE CASCADE);""";

        try (var stmt1 = dbContext.getPreparedStatement(query1); var stmt2 = dbContext.getPreparedStatement(query2)) {
            stmt1.execute();
            stmt2.execute();
        } catch (SQLException ignored) {
            logger.severe("Failed to create Events table with MTM relation");
        }
    }

    /**
     * Inserts a new event into the Events table.
     *
     * @param event The event object to be inserted.
     */
    @Override
    public void insert(Event event) {
        dbContext.getConnection();
        String query = "INSERT INTO Events (title, date, categoryId) VALUES (?, ?, ?);";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDate().toString());
            statement.setInt(3, event.getCategoryId());
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to insert Event");
        }
    }


    /**
     * Updates an existing event in the Events table.
     *
     * @param event The event object with updated information.
     */
    @Override
    public void update(Event event) {
        dbContext.getConnection();
        String query = "UPDATE Events SET title = ?, date = ? WHERE id = ?;";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDate().toString());
            statement.setInt(3, event.getId());
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to update Event");
        }
    }

    /**
     * Adds a contact to an event in the Events_Contacts table.
     *
     * @param eventId   The ID of the event.
     * @param contactId The ID of the contact.
     */
    @Override
    public void addContact(int eventId, int contactId) {
        dbContext.getConnection();
        String query = "INSERT INTO Events_Contacts (eventId, contactId) VALUES (?, ?);";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, eventId);
            statement.setInt(2, contactId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to add Contact to Event");
        }
    }

    /**
     * Removes a contact from an event in the Events_Contacts table.
     *
     * @param eventId   The ID of the event.
     * @param contactId The ID of the contact.
     */
    @Override
    public void removeContact(int eventId, int contactId) {
        dbContext.getConnection();
        String query = "DELETE FROM Events_Contacts WHERE eventId = ? AND contactId = ?;";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, eventId);
            statement.setInt(2, contactId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to remove Contact from Event");
        }
    }

    /**
     * Checks if an event with the given ID exists in the Events table.
     *
     * @param event The event object to check.
     * @return true if the event exists, false otherwise.
     */
    @Override
    public boolean isEventExist(Event event) {
        dbContext.getConnection();
        boolean exists = false;
        String query = "SELECT id FROM Events WHERE id = ?;";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, event.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException ignored) {
            logger.severe("Failed to check if Event exists");
        }
        return exists;
    }

    /**
     * Get all events, optionally filtered by category ID.
     *
     * @param id The category ID to filter events by. Can be null to retrieve all events.
     * @return A list of events.
     */
    @Override
    public List<Event> findAll(Integer id) {
        dbContext.getConnection();
        List<Event> events = new ArrayList<>();

        String query = """
                    SELECT
                        e.id AS eventId,
                        e.title AS eventTitle,
                        e.date AS eventDate,
                        c.id AS categoryId,
                        ct.id AS contactId,
                        ct.firstName AS contactFirstName,
                        ct.lastName AS contactLastName,
                        ct.phone AS contactPhone,
                        ct.categoryId AS contactCategoryId
                
                    FROM
                        Events e
                
                    LEFT JOIN
                        Categories c ON e.categoryId = c.id
                
                    LEFT JOIN
                        Events_Contacts ec ON e.id = ec.eventId
                
                    LEFT JOIN
                        Contacts ct ON ec.contactId = ct.id
                """;

        if (id != null) {
            query += " WHERE c.id = ?";
        }
        query += " ORDER BY e.date;";

        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            if (id != null) {
                statement.setInt(1, id);
            }
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, Event> eventMap = new LinkedHashMap<>();

            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                String eventTitle = resultSet.getString("eventTitle");
                LocalDate eventDate = LocalDate.parse(resultSet.getString("eventDate"));
                int categoryId = resultSet.getInt("categoryId");

                int contactId = resultSet.getInt("contactId");
                String contactFirstName = resultSet.getString("contactFirstName");
                String contactLastName = resultSet.getString("contactLastName");
                String contactPhone = resultSet.getString("contactPhone");
                int contactCategoryId = resultSet.getInt("contactCategoryId");

                Event event = eventMap.getOrDefault(eventId, new Event(
                        eventId, eventTitle, eventDate, new ArrayList<>(), categoryId
                ));
                if (event.getCategoryId() != categoryId) {
                    event.setCategoryId(categoryId);
                }
                if (contactId > 0) {
                    Contact contact = new Contact(
                            contactId, contactFirstName, contactLastName, contactPhone, contactCategoryId);
                    event.getParticipants().add(contact);
                }
                eventMap.put(eventId, event);
            }
            events = new ArrayList<>(eventMap.values());

        } catch (SQLException ignored) {
            logger.severe("Failed to find all Events with relations");
        }
        return events;
    }

    /**
     * Get all events for a specific category ID.
     *
     * @param categoryId The category ID to filter events by.
     * @return A list of events in the given category.
     */
    @Override
    public List<Event> findAllByCategoryId(int categoryId) {
        return this.findAll(categoryId);
    }

    /**
     * Deletes an event by its ID from the Events table.
     *
     * @param eventId The ID of the event to delete.
     */
    @Override
    public void deleteById(int eventId) {
        dbContext.getConnection();
        String query = "DELETE FROM Events WHERE id = ?";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setInt(1, eventId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to delete Event by id");
        }
    }

    /**
     * Deletes all events older than the given date from the Events table.
     *
     * @param date The date to filter events by.
     */
    @Override
    public void deleteOlderThan(LocalDate date) {
        dbContext.getConnection();
        String query = "DELETE FROM Events WHERE date < ?";
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, date.toString());
            statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.severe("Failed to delete Event by date");
        }
    }
}
