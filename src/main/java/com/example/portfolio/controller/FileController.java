package com.example.portfolio.controller;

import com.example.portfolio.entity.Certificate;
import com.example.portfolio.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private CertificateRepository repository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCertificate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) throws IOException {

        String uploadDir = "uploads/";

        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = uploadDir + file.getOriginalFilename();

        file.transferTo(new File(filePath));

        Certificate certificate = new Certificate();

        certificate.setName(name);
        certificate.setFilePath(filePath);

        repository.save(certificate);

        return ResponseEntity.ok("Certificate Uploaded");
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