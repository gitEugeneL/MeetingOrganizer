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

public class ContactRepository implements IContactRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final DbContext dbContext;
    public ContactRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    private Contact mapToContact(ResultSet resultSet) throws SQLException {
        return new Contact(
                resultSet.getInt("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("phone"),
                resultSet.getInt("categoryId")
        );
    }

    @Override
    public void createTable() {
        dbContext.getConnection();
        String query = """
                CREATE TABLE IF NOT EXISTS Contacts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    firstName VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    phone Varchar(15) NOT NULL UNIQUE,
                    categoryId INTEGER,
                    FOREIGN KEY(categoryId) REFERENCES Categories(id) ON DELETE CASCADE ON UPDATE CASCADE);
                """;
        try(PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.execute();
        } catch (SQLException ignored) {
            logger.info("Failed to create Contacts table");
        }
    }

    @Override
    public List<Contact> findAll() {
        dbContext.getConnection();
        String query = "SELECT id, firstName, lastName, phone, categoryId from Contacts;";
        List<Contact> contacts = new ArrayList<>();

        try(PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                contacts.add(this.mapToContact(resultSet));
            }
        } catch (SQLException ignored) {
            logger.info("Failed to find all Contacts");
        }
        return contacts;
    }

    @Override
    public void insert(Contact contact) {
        dbContext.getConnection();
        String query = "INSERT INTO Contacts (firstName, lastName, phone, categoryId) values (?, ?, ?, ?);";
        try(PreparedStatement statement = dbContext.getPreparedStatement(query)) {
           statement.setString(1, contact.getFirstName());
           statement.setString(2, contact.getLastName());
           statement.setString(3, contact.getPhone());
           statement.setInt(4, contact.getCategoryId());
           statement.executeUpdate();
        } catch (SQLException ignored) {
            logger.info("Failed to insert new Contact");
        }
    }

    @Override
    public Contact findContactByPhone(String phone) {
        dbContext.getConnection();
        String query = "SELECT id, firstName, lastName, phone, categoryId FROM Contacts WHERE phone = ?;";
        Contact contact = null;
        try (PreparedStatement statement = dbContext.getPreparedStatement(query)) {
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phoneNumber = resultSet.getString("phone");
                int categoryId = resultSet.getInt("categoryId");

                contact = new Contact(id, firstName, lastName, phoneNumber, categoryId);
            }
        } catch (SQLException e) {
            logger.info("Failed to find Contact by Phone");
        }
        return contact;
    }

}
