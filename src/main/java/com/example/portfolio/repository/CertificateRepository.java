package com.example.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.portfolio.entity.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}