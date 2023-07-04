package ua.kozminska.presentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.kozminska.domain.AppCommands;
import ua.kozminska.domain.data.Contact;
import ua.kozminska.domain.data.Relationships;
import ua.kozminska.domain.data.converters.ContactDataSource;
import ua.kozminska.domain.data.converters.GsonConverter;
import ua.kozminska.domain.data.converters.JsonConverter;
import ua.kozminska.domain.data.converters.LocalDateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateConverter())
                .create();
        JsonConverter gsonConverter = new GsonConverter(gson);
        ContactDataSource contactDataSource = new ContactDataSource(gsonConverter);
        List<Contact> contactList = contactDataSource.readContact();

        if (contactList == null) {
            contactList = new ArrayList<>();
        }

        AppCommands appCommands = new AppCommands(contactDataSource, contactList);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    -------- Список команд --------
                    0. Вихід
                    1. Додати контакт
                    2. Редагувати контакт
                    3. Видалити контакт
                    4. Показати всі контакти
                    5. Знайти контакт
                    """);
            String choice = scanner.nextLine();
            switch (choice) {
                case "0" -> {
                    System.out.println(">> Завершення роботи... ");
                    appCommands.saveChanges();
                    System.exit(0);
                }
                case "1" -> {
                    System.out.println(">> Введіть ім'я контакту:");
                    String name = scanner.nextLine();
                    System.out.println(">> Введіть прізвище контакту:");
                    String surname = scanner.nextLine();
                    System.out.println(">> Введіть номер телефону контакту:");
                    String phoneNumber = scanner.nextLine();
                    System.out.println(">> Введіть електронну адресу контакту:");
                    String email = scanner.nextLine();
                    System.out.println(">> Введіть дату народження контакту (рік-місяць-день):");
                    LocalDate birthDate = LocalDate.parse(scanner.nextLine());
                    System.out.println(">> Введіть стосунок до контакту :");
                    Relationships relationships = addRelationships(scanner);
                    System.out.println(">> Введіть ID контакту: ");
                    int id = scanner.nextInt();
                    Contact newContact = new Contact(name, surname, phoneNumber, email, birthDate, relationships, id);
                    appCommands.add(newContact);
                    contactDataSource.writeContact(contactList);
                    System.out.println(">> Контакт додано");
                }
                case "2" -> {
                    System.out.println(">> Введіть контакт який ви хочете оновити:");
                    String search = scanner.nextLine();
                    int idForUpdate = appCommands.search(search);
                    switch (idForUpdate) {
                        case -1 -> System.out.println(">> Контакт не знайдено.");
                        case -2 -> System.out.println(">> Знайдено декілька контактів.");
                        default -> {
                            System.out.println(">> Введіть нове ім'я контакту (або Enter для пропуску):");
                            String newName = scanner.nextLine();

                            System.out.println(">> Введіть нове прізвище контакту (або Enter для пропуску):");
                            String newSurname = scanner.nextLine();

                            System.out.println(">> Введіть новий номер телефону контакту (або Enter для пропуску):");
                            String newPhoneNumber = scanner.nextLine();

                            System.out.println(">> Введіть новий email контакту (або Enter для пропуску):");
                            String newEmail = scanner.nextLine();

                            System.out.println(">> Введіть нову дату народження контакту (у форматі YYYY-MM-DD, або Enter для пропуску):");
                            String newBirthDateString = scanner.nextLine();
                            LocalDate newBirthDate = newBirthDateString.isEmpty() ? LocalDate.of(0, 1, 1) : LocalDate.parse(newBirthDateString);

                            appCommands.updateContact(newName, newSurname, newPhoneNumber, newEmail, newBirthDate, idForUpdate);
                            contactDataSource.writeContact(contactList);
                        }
                    }
                }
                case "3" -> {
                    System.out.println(">> Введіть контакт, який потрібно видалити:");
                    String searchDelete = scanner.nextLine();
                    int idForDelete = appCommands.search(searchDelete);
                    switch (idForDelete){
                        case -1 -> System.out.println(">> Контакт не знайдено.");
                        case -2 -> System.out.println(">> Знайдено декілька контактів.");
                        default -> appCommands.delete(idForDelete);
                    }
                }
                case "4" -> {
                    System.out.println("-------- Всі контакти --------");
                    for (Contact contact : contactDataSource.readContact()) {
                        System.out.println(contact);
                    }
                }
                case "5" -> {
                    System.out.println(">> Введіть текст для пошуку:");
                    String search = scanner.nextLine();
                    int idForSearch= appCommands.search(search);
                    if (idForSearch == -1) System.out.println(">> Контакт не знайдено.");
                }

                default -> System.out.println(">> Не вірна команда. Спробуйте ще.");
            }
        }
    }
    public static Relationships addRelationships (Scanner scanner){
        Relationships relationships;
        System.out.println("""
                1. Siblings
                2. Parents
                3. Friends
                4. Boss
                5. Colleagues""");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> relationships = Relationships.SIBLINGS;
            case "2" -> relationships = Relationships.PARENTS;
            case "3" -> relationships = Relationships.FRIENDS;
            case "4" -> relationships = Relationships.BOSS;
            case "5" -> relationships = Relationships.COLLEAGUES;
            default -> relationships = null;
        }
        return relationships;
    }
}