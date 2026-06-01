package com.example.portfolio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.portfolio.entity.Admin;
import com.example.portfolio.entity.AboutItem;
import com.example.portfolio.entity.Certificate;
import com.example.portfolio.entity.Project;
import com.example.portfolio.entity.SkillItem;
import com.example.portfolio.entity.WorkExperience;
import com.example.portfolio.repository.AdminRepository;
import com.example.portfolio.repository.AboutItemRepository;
import com.example.portfolio.repository.CertificateRepository;
import com.example.portfolio.repository.ProjectRepository;
import com.example.portfolio.repository.SkillItemRepository;
import com.example.portfolio.repository.WorkExperienceRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final ProjectRepository projectRepository;
    private final CertificateRepository certificateRepository;
    private final AboutItemRepository aboutItemRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final SkillItemRepository skillItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:}")
    private String adminUsername;

    @Value("${app.admin.password:}")
    private String adminPassword;

    public DataInitializer(
            AdminRepository adminRepository,
            ProjectRepository projectRepository,
            CertificateRepository certificateRepository,
            AboutItemRepository aboutItemRepository,
            WorkExperienceRepository workExperienceRepository,
            SkillItemRepository skillItemRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.projectRepository = projectRepository;
        this.certificateRepository = certificateRepository;
        this.aboutItemRepository = aboutItemRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.skillItemRepository = skillItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (adminUsername != null && !adminUsername.isBlank() && adminPassword != null && !adminPassword.isBlank()) {
            adminRepository.findByUsername(adminUsername).map(admin -> {
                admin.setPassword(passwordEncoder.encode(adminPassword));
                return adminRepository.save(admin);
            }).orElseGet(() -> {
                    Admin admin = new Admin();
                    admin.setUsername(adminUsername);
                    admin.setPassword(passwordEncoder.encode(adminPassword));
                    return adminRepository.save(admin);
            });
        }

        if (projectRepository.count() == 0) {
            seedProject("Laundry Management System", "A comprehensive Spring Boot backend system featuring authentication, user management, admin controls, delivery management, shop management, and complete order handling workflows.", "Spring Boot,Java,MySQL,REST API");
            seedProject("E-Commerce Platform", "Full-stack e-commerce application with product catalog, shopping cart, user authentication, and secure checkout process. Built with modern web technologies.", "React,Spring Boot,MySQL,Bootstrap");
            seedProject("CRUD Application", "A Login and Registration System built using Java Servlets, JSP, and MySQL. Implements secure authentication and complete CRUD operations for user management.", "Java Servlets,JSP,MySQL,JDBC");
        }

        if (certificateRepository.count() == 0) {
            seedCertificate("Web Development in JAVA", "Technoglobe", "120 Days", "Jaipur - Gopalpura", "16 Feb 2026", "Training Certificate", "/certificates/Technoglobe.png");
            seedCertificate("Web Page Designing & Development Program", "BECIL (Broadcast Engineering Consultants India Limited)", "2 Months", "Jaipur", "22 Nov 2025", "Course Certificate", "/certificates/Becil.png");
            seedCertificate("API Integration Specialist", "Growbizz.io", "3 Months", "Jaipur", "18 Mar 2026", "Experience Letter", "/certificates/Growbizz.png");
        }

        if (aboutItemRepository.count() == 0) {
            seedAboutItem("Professional Experience", "Java Full Stack Developer Trainee at Technoglobe, working on real-world projects and REST APIs.", "briefcase");
            seedAboutItem("Technical Skills", "Proficient in Java, Spring Boot, React, and MySQL with hands-on project experience.", "code");
            seedAboutItem("Education", "B.Com from R.N. Ruia Government College, continuously learning new technologies.", "education");
            seedAboutItem("Team Player", "Strong teamwork and communication skills developed through collaborative projects.", "team");
        }

        if (workExperienceRepository.count() == 0) {
            seedExperience("API Integration Specialist", "Growbizz.io | Dec 2025 - Mar 2026", "Integrated APIs and collaborated with team members while maintaining a professional and positive work attitude. Demonstrated strong technical expertise.");
        }

        if (skillItemRepository.count() == 0) {
            seedSkill("Frontend", "HTML", 90);
            seedSkill("Frontend", "CSS", 85);
            seedSkill("Frontend", "JavaScript", 80);
            seedSkill("Frontend", "React", 75);
            seedSkill("Frontend", "Bootstrap", 85);
            seedSkill("Backend", "Java", 85);
            seedSkill("Backend", "Spring Boot", 80);
            seedSkill("Backend", "REST APIs", 80);
            seedSkill("Backend", "Servlets", 75);
            seedSkill("Backend", "JDBC", 75);
            seedSkill("Database & Tools", "MySQL", 80);
            seedSkill("Database & Tools", "GitHub", 80);
            seedSkill("Database & Tools", "VS Code", 85);
            seedSkill("Database & Tools", "Eclipse", 80);
            seedSkill("AI Tools", "ChatGPT", 85);
            seedSkill("AI Tools", "GitHub Copilot", 80);
            seedSkill("AI Tools", "Gemini", 75);
            seedSkill("AI Tools", "Cursor", 75);
            seedSkill("AI Tools", "Claude Code", 75);
            seedSkill("AI Tools", "Codex", 80);
        }
    }

    private void seedProject(String title, String description, String technologies) {
        boolean exists = projectRepository.findAll().stream()
                .anyMatch(project -> title.equalsIgnoreCase(project.getTitle()));
        if (exists) {
            return;
        }

        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setTechnologies(technologies);
        project.setGithubUrl("https://github.com/Ravisaini0");
        projectRepository.save(project);
    }

    private void seedCertificate(String title, String issuer, String duration, String location, String date, String type, String imageUrl) {
        boolean exists = certificateRepository.findAll().stream()
                .anyMatch(certificate -> title.equalsIgnoreCase(certificate.getTitle()) || title.equalsIgnoreCase(certificate.getName()));
        if (exists) {
            return;
        }

        Certificate certificate = new Certificate();
        certificate.setName(title);
        certificate.setTitle(title);
        certificate.setIssuer(issuer);
        certificate.setDuration(duration);
        certificate.setLocation(location);
        certificate.setDate(date);
        certificate.setType(type);
        certificate.setImageUrl(imageUrl);
        certificateRepository.save(certificate);
    }

    private void seedAboutItem(String title, String description, String icon) {
        boolean exists = aboutItemRepository.findAll().stream()
                .anyMatch(item -> title.equalsIgnoreCase(item.getTitle()));
        if (exists) {
            return;
        }

        AboutItem item = new AboutItem();
        item.setTitle(title);
        item.setDescription(description);
        item.setIcon(icon);
        aboutItemRepository.save(item);
    }

    private void seedExperience(String title, String companyPeriod, String description) {
        boolean exists = workExperienceRepository.findAll().stream()
                .anyMatch(experience -> title.equalsIgnoreCase(experience.getTitle()));
        if (exists) {
            return;
        }

        WorkExperience experience = new WorkExperience();
        experience.setTitle(title);
        experience.setCompanyPeriod(companyPeriod);
        experience.setDescription(description);
        workExperienceRepository.save(experience);
    }

    private void seedSkill(String category, String name, Integer level) {
        boolean exists = skillItemRepository.findAll().stream()
                .anyMatch(skill -> category.equalsIgnoreCase(skill.getCategory()) && name.equalsIgnoreCase(skill.getName()));
        if (exists) {
            return;
        }

        SkillItem skill = new SkillItem();
        skill.setCategory(category);
        skill.setName(name);
        skill.setLevel(level);
        skillItemRepository.save(skill);
    }
}
