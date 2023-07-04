package ua.kozminska.domain.data.converters;

import ua.kozminska.domain.data.Contact;

import java.util.List;

public interface JsonConverter {
    String toJson(List<Contact> contactList);
    List<Contact> fromJson(String contactList);
}