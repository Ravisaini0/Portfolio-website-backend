package com.example.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.SkillItem;

public interface SkillItemRepository extends JpaRepository<SkillItem, Long> {
}
