package com.example.myspringapp.service;

import com.example.myspringapp.model.Contact;
import com.example.myspringapp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    private ContactRepository repository;
    private ContactService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ContactRepository.class);
        service = new ContactService(repository);
    }

    @Test
    void testSave() {
        Contact contact = new Contact("Ivanov", "+123456789");
        when(repository.save(contact)).thenReturn(contact);

        Contact saved = service.save(contact);
        assertEquals(contact, saved);
        verify(repository, times(1)).save(contact);
    }

    @Test
    void testFindAll() {
        List<Contact> contacts = List.of(
                new Contact("Ivanov", "+123456789"),
                new Contact("Petrov", "+987654321")
        );
        when(repository.findAll()).thenReturn(contacts);

        List<Contact> result = service.findAll();
        assertEquals(2, result.size());
        assertEquals("Ivanov", result.get(0).getLastName());
        verify(repository, times(1)).findAll();
    }
}
