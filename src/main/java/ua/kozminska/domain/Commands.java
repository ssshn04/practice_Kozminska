package ua.kozminska.domain;

import ua.kozminska.domain.data.Contact;

import java.time.LocalDate;

public interface Commands {
    void add(Contact contact);

    void delete(int delete);

    int search(String search);

    void updateContact(String newName, String newSurname, String newPhoneNumber,
                       String newEmail, LocalDate newBirthDate, int id);

    void saveChanges();
}
