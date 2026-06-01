package com.example.portfolio.controller;

import com.example.portfolio.entity.Certificate;
import com.example.portfolio.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping({"/api/files", "/api/certificates"})
public class FileController {

    @Autowired
    private CertificateRepository repository;

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

        Path uploadDir = Path.of("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);

        String originalName = file.getOriginalFilename() == null ? "certificate" : file.getOriginalFilename();
        String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "-");
        String fileName = System.currentTimeMillis() + "-" + safeName;
        Path target = uploadDir.resolve(fileName).normalize();
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        String filePath = target.toString();

        Certificate certificate = id == null
                ? new Certificate()
                : repository.findById(id).orElseGet(Certificate::new);

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
        certificate.setFilePath(filePath);
        certificate.setImageUrl("/uploads/" + fileName);

        Certificate saved = repository.save(certificate);

        return ResponseEntity.ok(saved);
    }

    @PostMapping
    public ResponseEntity<?> addCertificate(@RequestBody Certificate certificate) {
        if (certificate.getName() == null || certificate.getName().isBlank()) {
            certificate.setName(certificate.getTitle());
        }
        return ResponseEntity.ok(repository.save(certificate));
    }

    @GetMapping
    public ResponseEntity<?> getAllCertificates() {
        return ResponseEntity.ok(repository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.ok("Deleted Successfully");
    }
}
