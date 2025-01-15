package com.euglihon.meetingorganizer.data;

import com.euglihon.meetingorganizer.model.Category;
import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.service.IEventService;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.logging.Logger;

/**
 * Exporter class responsible for exporting data to XML format.
 */
public class Exporter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ICategoryService categoryService;
    private final IContactService contactService;
    private final IEventService eventService;

    /**
     * Constructs an Exporter object with the specified services.
     *
     * @param categoryService the service for managing categories
     * @param contactService the service for managing contacts
     * @param eventService the service for managing events
     */
    public Exporter(ICategoryService categoryService, IContactService contactService, IEventService eventService) {
        this.categoryService = categoryService;
        this.contactService = contactService;
        this.eventService = eventService;
    }

    /**
     * Exports the data to an XML file located at the specified path.
     *
     * @param filePath the path where the XML file will be saved
     */
    public void exportToXML(String filePath) {

        try {
            // Create a new DocumentBuilderFactory instance
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Create root XMl element
            Element root = document.createElement("data");
            document.appendChild(root);

            // Export category
            this.createCategory(root, document);
            // Export contacts
            this.createContact(root, document);
            // Export events
            this.createEvent(root, document);

            // Save XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Database exported to XML successfully!");
        } catch (Exception e) {
            logger.severe("Error during XML export: " + e);
        }
    }

    /**
     * Creates XML elements for categories and appends them to the root element.
     *
     * @param root the root XML element
     * @param document the XML document
     */
    private void createCategory(Element root, Document document) {
        Element categoriesElement = document.createElement("categories");
        root.appendChild(categoriesElement);
        for (Category category : this.categoryService.getAllCategories()) {
            Element categoryElement = document.createElement("category");
            categoriesElement.appendChild(categoryElement);

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(String.valueOf(category.getId())));
            categoryElement.appendChild(id);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(category.getName()));
            categoryElement.appendChild(name);

            Element color = document.createElement("color");
            color.appendChild(document.createTextNode(category.toString()));
            categoryElement.appendChild(color);
        }
    }

    /**
     * Creates XML elements for contacts and appends them to the root element.
     *
     * @param root the root XML element
     * @param document the XML document
     */
    private void createContact(Element root, Document document) {
        Element contactsElement = document.createElement("contacts");
        root.appendChild(contactsElement);
        for (Contact contact : this.contactService.getAllContacts()) {
            Element contactElement = document.createElement("contact");
            contactsElement.appendChild(contactElement);

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(String.valueOf(contact.getId())));
            contactElement.appendChild(id);

            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode(contact.getFirstName()));
            contactElement.appendChild(firstName);

            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(contact.getLastName()));
            contactElement.appendChild(lastName);

            Element phone = document.createElement("phone");
            phone.appendChild(document.createTextNode(contact.getPhone()));
            contactElement.appendChild(phone);

            Element categoryId = document.createElement("categoryId");
            categoryId.appendChild(document.createTextNode(String.valueOf(contact.getCategoryId())));
            contactElement.appendChild(categoryId);
        }
    }

    /**
     * Creates XML elements for events and appends them to the root element.
     * Also includes participants for each event.
     *
     * @param root the root XML element
     * @param document the XML document
     */
    private void createEvent(Element root, Document document) {
        Element eventsElement = document.createElement("events");
        root.appendChild(eventsElement);
        for (Event event : this.eventService.getAllEvents()) {
            Element eventElement = document.createElement("event");
            eventsElement.appendChild(eventElement);

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(String.valueOf(event.getId())));
            eventElement.appendChild(id);

            Element title = document.createElement("title");
            title.appendChild(document.createTextNode(event.getTitle()));
            eventElement.appendChild(title);

            Element date = document.createElement("date");
            date.appendChild(document.createTextNode(event.getDate().toString()));
            eventElement.appendChild(date);

            Element categoryId = document.createElement("categoryId");
            categoryId.appendChild(document.createTextNode(String.valueOf(event.getCategoryId())));
            eventElement.appendChild(categoryId);

            Element participantsElement = document.createElement("participants");
            eventElement.appendChild(participantsElement);
            for (Contact participant : event.getParticipants()) {
                Element participantElement = document.createElement("participant");
                participantsElement.appendChild(participantElement);

                Element participantId = document.createElement("id");
                participantId.appendChild(document.createTextNode(String.valueOf(participant.getId())));
                participantElement.appendChild(participantId);

                Element participantName = document.createElement("name");
                participantName.appendChild(document.createTextNode(participant.getFirstName() + " " + participant.getLastName()));
                participantElement.appendChild(participantName);
            }
        }
    }
}
