package com.euglihon.meetingorganizer;

import com.euglihon.meetingorganizer.console.CategoryConsole;
import com.euglihon.meetingorganizer.console.ContactConsole;
import com.euglihon.meetingorganizer.console.EventConsole;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.service.IEventService;

import java.util.Scanner;

/**
 * Handles the text-based user interface for the Meeting Organizer application.
 * Provides a console interface for users to manage events, contacts, and categories.
 */
public class TextMode {

    private final ICategoryService categoryService;
    private final IContactService contactService;
    private final IEventService eventService;

    /**
     * Constructor for initializing the TextMode with required services.
     *
     * @param categoryService Service for category management.
     * @param contactService  Service for contact management.
     * @param eventService    Service for event management.
     */
    public TextMode(ICategoryService categoryService, IContactService contactService, IEventService eventService) {
        this.categoryService = categoryService;
        this.contactService = contactService;
        this.eventService = eventService;
    }

    /**
     * Starts the text-based interface, allowing the user to interact with the application via console commands.
     */
    public void start() {
        // Initialize console handlers for categories, contacts, and events
        CategoryConsole categoryConsole = new CategoryConsole(this.categoryService);
        ContactConsole contactConsole = new ContactConsole(this.contactService);
        EventConsole eventConsole = new EventConsole(this.eventService);

        System.out.println("Text mode");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Main loop for the text-based interface
        while (running) {
            System.out.println("----------------------------:");
            System.out.println("1. Show all events");
            System.out.println("2. Show all contacts");
            System.out.println("3. Show all categories");
            System.out.println("------------------:");
            System.out.println("4. Delete event");
            System.out.println("5. Delete contact");
            System.out.println("6. Delete category");
            System.out.println("------------------:");
            System.out.println("7. Create event");
            System.out.println("8. Create contact");
            System.out.println("9. Create category");
            System.out.println("------------------:");
            System.out.println("10. Include participant to event");
            System.out.println("11. Exclude participant from event");
            System.out.println("------------------:");
            System.out.println("-1. Exit");
            System.out.println("----------------------------:");

            // Read user input
            String choice = scanner.nextLine();

            // Handle user input based on their selection
            switch (choice) {
                case "1" -> eventConsole.showAllEvents();
                case "2" -> contactConsole.showAllContacts();
                case "3" -> categoryConsole.showAllCategories();
                case "4" -> eventConsole.deleteEvent();
                case "5" -> contactConsole.deleteContact();
                case "7" -> eventConsole.createEvent();
                case "6" -> categoryConsole.deleteCategory();
                case "8" -> contactConsole.createContact();
                case "9" -> categoryConsole.createCategory();
                case "10" -> eventConsole.includeParticipant();
                case "11" -> eventConsole.excludeParticipant();
                case "-1" -> {
                    running = false;
                    System.out.println("Exiting program...");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        // Close the scanner and exit the application
        scanner.close();
        System.exit(0);
    }
}