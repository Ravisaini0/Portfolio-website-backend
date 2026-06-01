package com.example.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.portfolio.entity.Contact;
import com.example.portfolio.repository.ContactRepository;

@Service
public class ContactService {
@Autowired
private ContactRepository repo;

public Contact save(Contact contact) {
	return repo.save(contact);
}

public java.util.List<Contact> findAll() {
	return repo.findAll();
}

public void delete(Long id) {
	repo.deleteById(id);
}
}
