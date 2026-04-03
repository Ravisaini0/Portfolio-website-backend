package com.example.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact,Long> {

}
