package com.euglihon.meetingorganizer.console;

import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.model.Event;
import com.euglihon.meetingorganizer.service.IEventService;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EventConsole {

    private final IEventService eventService;
    public EventConsole(IEventService eventService) {
        this.eventService = eventService;
    }

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

    public void deleteEvent() {
        System.out.print("Enter event ID to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        eventService.deleteEventById(id);
    }

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

    public void includeParticipant() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter event ID: ");
        int eventId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter contact ID: ");
        int contactId = Integer.parseInt(scanner.nextLine());

        this.eventService.addContactToEvent(eventId, contactId);
    }

    public void excludeParticipant() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter event ID: ");
        int eventId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter contact ID: ");
        int contactId = Integer.parseInt(scanner.nextLine());

        this.eventService.removeContactFromEvent(eventId, contactId);
    }
}
