package com.example.myspringapp.controller;

import com.example.myspringapp.model.Contact;
import com.example.myspringapp.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactControllerTest {

    private ContactService service;
    private ContactController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        service = mock(ContactService.class);
        controller = new ContactController(service);
        model = mock(Model.class);
    }

    @Test
    void testShowAddForm() {
        String viewName = controller.showAddForm(model);
        assertEquals("add-contact", viewName);
        verify(model, times(1)).addAttribute(eq("contact"), any(Contact.class));
    }

    @Test
    void testAddContact() {
        Contact contact = new Contact("Ivanov", "+123456789");

        String redirect = controller.addContact(contact);
        assertEquals("redirect:/contacts", redirect);
        verify(service, times(1)).save(contact);
    }

    @Test
    void testListContacts() {
        List<Contact> contacts = List.of(
                new Contact("Ivanov", "+123456789"),
                new Contact("Petrov", "+987654321")
        );
        when(service.findAll()).thenReturn(contacts);

        String viewName = controller.listContacts(model);
        assertEquals("contacts", viewName);
        verify(service, times(1)).findAll();
        verify(model, times(1)).addAttribute("contacts", contacts);
    }
}
