package com.euglihon.meetingorganizer.console;

import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.service.IEventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * EventConsole class responsible for interacting with the user
 * to manage events in the console interface.
 */
public class EventConsole {

    private final IEventService eventService;

    /**
     * Constructs an EventConsole with the specified event service.
     *
     * @param eventService the service for managing events
     */
    public EventConsole(IEventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Displays all events with their IDs, titles, dates, category IDs, and participants.
     * If no events are found, displays a message indicating so.
     */
    public void showAllEvents() {
        List<Event> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else  {
            System.out.println("\nEvents:");
            for (Event event : events) {
                System.out.print("Id: " + event.getId()
                        + ", Title: " + event.getTitle()
                        + ", Date: " + event.getDate().toString()
                        + ", CategoryId: " + event.getCategoryId()
                        + ", ParticipantId: ");

                for (Contact participant : event.getParticipants()) {
                    System.out.print(participant.getId() + " "
                            + participant.getFirstName() + " "
                            + participant.getLastName() + " | ");
                }
                System.out.println();
            }
        }
    }

    /**
     * Deletes an event based on the ID entered by the user.
     * Prompts the user to input the ID of the event to delete.
     */
    public void deleteEvent() {
        System.out.print("Enter event ID to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        eventService.deleteEventById(id);
    }

    /**
     * Creates a new event based on user input.
     * Prompts the user to input the event title, date, and category ID.
     */
    public void createEvent() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter event title: ");
        String title = scanner.nextLine();

        System.out.print("Enter event date (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateString);

        System.out.print("Enter category ID: ");
        int categoryId = Integer.parseInt(scanner.nextLine());

        Event event = new Event();
        event.setTitle(title);
        event.setDate(date);
        event.setCategoryId(categoryId);
        this.eventService.addEvent(event);
    }

    /**
     * Includes a participant in an event.
     * Prompts the user to input the event ID and the contact ID of the participant.
     */
    public void includeParticipant() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter event ID: ");
        int eventId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter contact ID: ");
        int contactId = Integer.parseInt(scanner.nextLine());

        this.eventService.addContactToEvent(eventId, contactId);
    }

    /**
     * Excludes a participant from an event.
     * Prompts the user to input the event ID and the contact ID of the participant.
     */
    public void excludeParticipant() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter event ID: ");
        int eventId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter contact ID: ");
        int contactId = Integer.parseInt(scanner.nextLine());

        this.eventService.removeContactFromEvent(eventId, contactId);
    }
}
