package com.example.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
