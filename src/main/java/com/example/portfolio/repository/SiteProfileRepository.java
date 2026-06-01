package com.example.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.SiteProfile;

public interface SiteProfileRepository extends JpaRepository<SiteProfile, Long> {
}
