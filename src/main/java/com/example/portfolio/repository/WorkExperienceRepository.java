package com.example.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.WorkExperience;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
}
