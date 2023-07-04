package ua.kozminska.domain;

import ua.kozminska.domain.data.Contact;
import ua.kozminska.domain.data.converters.ContactDataSource;

import java.time.LocalDate;
import java.util.List;

public class AppCommands implements Commands {
    private final ContactDataSource contactDataSource;
    private final List<Contact> contacts;

    public AppCommands(ContactDataSource contactDataSource, List<Contact> contacts) {
        this.contactDataSource = contactDataSource;
        this.contacts = contacts;
    }

    @Override
    public void add(Contact contact) {
        contacts.add(contact);
    }

    public void delete(int id) {
        int index = -1;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == id) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            contacts.remove(index);
            System.out.println(">> Контакт видалено");
            contactDataSource.writeContact(contacts);
        } else {
            System.out.println(">> Контакт не знайдено.");
        }
    }


    @Override
    public int search(String search) {
        int id = -1;
        int i = 0;
        for (Contact contact : contacts) {
            if (contact.getName().contains(search) || contact.getSurname().contains(search)) {
                System.out.println(contact);
                id = contact.getId();
                i++;
            }
        }
        if (i > 1) return -2;
        else return id;
    }
    @Override
    public void updateContact(String newName, String newSurname, String newPhoneNumber,
                              String newEmail, LocalDate newBirthDate, int id) {
        LocalDate comparison = LocalDate.of(0, 1, 1);
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                if (!newName.isEmpty()) contact.setName(newName);
                if (!newSurname.isEmpty()) contact.setSurname(newSurname);
                if (!newPhoneNumber.isEmpty()) contact.setPhoneNumber(newPhoneNumber);
                if (!newEmail.isEmpty()) contact.setEmail(newEmail);
                if (newBirthDate.compareTo(comparison) != 0) contact.setBirthDate(newBirthDate);
                System.out.println(">> Контакт оновлено");
                contactDataSource.writeContact(contacts);
                return;
            }
        }
        System.out.println(">> Контакт з ID " + id + " не знайдено.");
    }

    @Override
    public void saveChanges() {
        contactDataSource.writeContact(contacts);
    }
}
