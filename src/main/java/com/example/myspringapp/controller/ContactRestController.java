package com.example.myspringapp.controller;

import com.example.myspringapp.model.Contact;
import com.example.myspringapp.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactRestController {

    private final ContactService service;

    public ContactRestController(ContactService service) {
        this.service = service;
    }

    // Получить список всех контактов в JSON
    @GetMapping
    public List<Contact> getAllContacts() {
        return service.findAll();
    }

    // Добавить новый контакт, данные приходят в JSON, возвращаем сохранённый контакт
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        return service.save(contact);
    }
}
