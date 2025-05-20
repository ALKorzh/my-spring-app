package com.example.myspringapp.service;

import com.example.myspringapp.model.Contact;
import com.example.myspringapp.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    public List<Contact> findAll() {
        return repository.findAll();
    }
}
