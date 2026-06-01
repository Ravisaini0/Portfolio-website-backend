package com.example.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.AboutItem;

public interface AboutItemRepository extends JpaRepository<AboutItem, Long> {
}
