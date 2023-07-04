package ua.kozminska.domain.data;

import java.time.LocalDate;

public class Contact {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private final Relationships relationships;
    private final int id;


    public Contact(String name, String surname, String phoneNumber, String email, LocalDate birthDate, Relationships relationships, int id) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.relationships = relationships;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n"
                + "Ім'я: " + name + "\n"
                + "Прізвище: " + surname + "\n"
                + "Номер телефону: " + phoneNumber + "\n"
                + "E-mail: " + email + "\n"
                + "Дата народження: " + birthDate + "\n"
                + "Стосунки: " + relationships + "\n"
                + "--------  ***  --------";
    }
}