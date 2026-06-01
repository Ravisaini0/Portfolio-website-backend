package com.example.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.portfolio.entity.Contact;
import com.example.portfolio.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService service;

    @PostMapping
    public Contact saveContact(@Valid @RequestBody Contact contact) {
        return service.save(contact);
    }

    @GetMapping
    public java.util.List<Contact> getContacts() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        service.delete(id);
    }
}
