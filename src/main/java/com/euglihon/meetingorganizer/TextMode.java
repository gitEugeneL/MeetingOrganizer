package com.euglihon.meetingorganizer;

import com.euglihon.meetingorganizer.console.CategoryConsole;
import com.euglihon.meetingorganizer.console.ContactConsole;
import com.euglihon.meetingorganizer.console.EventConsole;
import com.euglihon.meetingorganizer.service.ICategoryService;
import com.euglihon.meetingorganizer.service.IContactService;
import com.euglihon.meetingorganizer.service.IEventService;

import java.util.Scanner;

public class TextMode {

    private final ICategoryService categoryService;
    private final IContactService contactService;
    private final IEventService eventService;

    public TextMode(ICategoryService categoryService, IContactService contactService, IEventService eventService) {
        this.categoryService = categoryService;
        this.contactService = contactService;
        this.eventService = eventService;
    }

    public void start() {
        CategoryConsole categoryConsole = new CategoryConsole(this.categoryService);
        ContactConsole contactConsole = new ContactConsole(this.contactService);
        EventConsole eventConsole = new EventConsole(this.eventService);

        System.out.println("Text mode");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

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

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    eventConsole.showAllEvents();
                    break;
                case "2":
                    contactConsole.showAllContacts();
                    break;
                case "3":
                    categoryConsole.showAllCategories();
                    break;
                case "4":
                    eventConsole.deleteEvent();
                    break;
                case "5":
                    contactConsole.deleteContact();
                    break;
                case "7":
                    eventConsole.createEvent();
                    break;
                case "6":
                    categoryConsole.deleteCategory();
                    break;
                case "8":
                    contactConsole.createContact();
                    break;
                case "9":
                    categoryConsole.createCategory();
                    break;
                case "10":
                    eventConsole.includeParticipant();
                    break;
                case "11":
                    eventConsole.excludeParticipant();
                    break;
                case "-1":
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
        System.exit(0);
    }
}