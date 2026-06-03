package com.example.portfolio.controller;

import com.example.portfolio.entity.Certificate;
import com.example.portfolio.repository.CertificateRepository;
import com.example.portfolio.service.UploadStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping({"/api/files", "/api/certificates"})
public class FileController {

    @Autowired
    private CertificateRepository repository;

    @Autowired
    private UploadStorageService uploadStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCertificate(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam(value = "issuer", required = false) String issuer,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "duration", required = false) String duration,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "description", required = false) String description
    ) throws IOException {
        String uploadPath = uploadStorageService.store(file);

        Certificate certificate = id == null
                ? new Certificate()
                : repository.findById(id).orElseGet(Certificate::new);

        if (id != null && certificate.getId() != null) {
            uploadStorageService.deleteIfLocalUpload(certificate.getImageUrl());
            uploadStorageService.deleteIfLocalUpload(certificate.getFilePath());
        }

        if (id != null) {
            certificate.setId(id);
        }

        certificate.setName(name);
        certificate.setTitle(name);
        certificate.setIssuer(issuer);
        certificate.setDate(date);
        certificate.setDuration(duration);
        certificate.setLocation(location);
        certificate.setType(type);
        certificate.setDescription(description);
        certificate.setFilePath(uploadPath);
        certificate.setImageUrl(uploadPath);

        Certificate saved = repository.save(certificate);

        return ResponseEntity.ok(saved);
    }

    @PostMapping
    public ResponseEntity<?> addCertificate(@RequestBody Certificate certificate) {
        if (certificate.getName() == null || certificate.getName().isBlank()) {
            certificate.setName(certificate.getTitle());
        }
        if (certificate.getId() != null) {
            repository.findById(certificate.getId())
                    .map(Certificate::getImageUrl)
                    .filter(oldImage -> isDifferent(oldImage, certificate.getImageUrl()))
                    .ifPresent(uploadStorageService::deleteIfLocalUpload);
        }
        return ResponseEntity.ok(repository.save(certificate));
    }

    @GetMapping
    public ResponseEntity<?> getAllCertificates() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable Long id) {
        repository.findById(id).ifPresent(certificate -> {
            uploadStorageService.deleteIfLocalUpload(certificate.getImageUrl());
            uploadStorageService.deleteIfLocalUpload(certificate.getFilePath());
        });
        repository.deleteById(id);

        return ResponseEntity.ok("Deleted Successfully");
    }

    private boolean isDifferent(String oldValue, String newValue) {
        String oldText = oldValue == null ? "" : oldValue.trim();
        String newText = newValue == null ? "" : newValue.trim();
        return !oldText.equals(newText);
    }
}
