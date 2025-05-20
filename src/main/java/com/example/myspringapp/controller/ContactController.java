package com.example.myspringapp.controller;

import com.example.myspringapp.model.Contact;
import com.example.myspringapp.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    // Форма добавления контакта
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "add-contact";
    }

    // Обработка добавления контакта
    @PostMapping("/add")
    public String addContact(@ModelAttribute Contact contact) {
        service.save(contact);
        return "redirect:/contacts";
    }

    // Страница со списком контактов
    @GetMapping
    public String listContacts(Model model) {
        model.addAttribute("contacts", service.findAll());
        return "contacts";
    }
}
