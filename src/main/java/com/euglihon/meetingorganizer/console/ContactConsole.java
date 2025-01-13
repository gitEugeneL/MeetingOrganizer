package com.euglihon.meetingorganizer.console;

import com.euglihon.meetingorganizer.model.Contact;
import com.euglihon.meetingorganizer.service.IContactService;

import java.util.List;
import java.util.Scanner;

public class ContactConsole {

    private final IContactService contactService;
    public ContactConsole(IContactService contactService) {
        this.contactService = contactService;
    }

    public void showAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("\nContacts:");
            for (Contact contact : contacts) {
                System.out.println("ID: " + contact.getId()
                        + ", FirstName: " + contact.getFirstName()
                        + ", LastName: " + contact.getLastName()
                        + ", Phone: " + contact.getPhone()
                        + ", CategoryId: " + contact.getCategoryId());
            }
        }
    }

    public void deleteContact() {
        System.out.print("Enter contact ID to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        contactService.deleteContactById(id);
    }

    public void createContact() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter category ID: ");
        int categoryId = Integer.parseInt(scanner.nextLine());

        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhone(phone);
        contact.setCategoryId(categoryId);

        this.contactService.addContact(contact);
    }
}
