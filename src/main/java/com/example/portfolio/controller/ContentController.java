package com.example.portfolio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.portfolio.entity.Project;
import com.example.portfolio.entity.SiteProfile;
import com.example.portfolio.repository.ProjectRepository;
import com.example.portfolio.repository.SiteProfileRepository;

@RestController
@RequestMapping("/api")
public class ContentController {

    private final SiteProfileRepository siteProfileRepository;
    private final ProjectRepository projectRepository;

    public ContentController(SiteProfileRepository siteProfileRepository, ProjectRepository projectRepository) {
        this.siteProfileRepository = siteProfileRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/profile")
    public SiteProfile getProfile() {
        return siteProfileRepository.findById(1L).orElseGet(this::defaultProfile);
    }

    @PutMapping("/profile")
    public SiteProfile updateProfile(@RequestBody SiteProfile profile) {
        profile.setId(1L);
        return siteProfileRepository.save(profile);
    }

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @PostMapping("/projects")
    public Project saveProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
    }

    @PostMapping("/uploads")
    public java.util.Map<String, String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        Path uploadDir = Path.of("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);

        String originalName = file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename();
        String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "-");
        String fileName = System.currentTimeMillis() + "-" + safeName;
        Path target = uploadDir.resolve(fileName).normalize();
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return java.util.Map.of("url", "/uploads/" + fileName);
    }

    private SiteProfile defaultProfile() {
        SiteProfile profile = new SiteProfile();
        profile.setId(1L);
        profile.setFullName("Ravi Saini");
        profile.setRole("Java Full Stack Developer");
        profile.setBio("Passionate about building responsive websites and REST APIs. Skilled in Java, Spring Boot, React, and MySQL. Always eager to solve real-world problems through code.");
        profile.setEmail("ravisaini61245@gmail.com");
        profile.setPhone("+91 7877940013");
        profile.setLocation("Jaipur, India");
        profile.setGithubUrl("https://github.com/Ravisaini0");
        profile.setLinkedinUrl("https://www.linkedin.com/in/ravi-saini-822300396");
        profile.setProfileImageUrl("/images/ravi-profile.jpeg");
        profile.setAboutTitle("Know Who I Am");
        profile.setAboutDescription("I'm a Java Full Stack Developer with hands-on experience in building web applications and REST APIs. I have a strong foundation in both frontend and backend technologies, and I'm passionate about creating efficient, scalable solutions.");
        profile.setHighlightExperience("Java Full Stack Developer Trainee at Technoglobe, working on real-world projects and REST APIs.");
        profile.setHighlightSkills("Proficient in Java, Spring Boot, React, and MySQL with hands-on project experience.");
        profile.setHighlightEducation("B.Com from R.N. Ruia Government College, continuously learning new technologies.");
        profile.setHighlightTeamwork("Strong teamwork and communication skills developed through collaborative projects.");
        profile.setWorkTitle("API Integration Specialist");
        profile.setWorkCompanyPeriod("Growbizz.io | Dec 2025 - Mar 2026");
        profile.setWorkDescription("Integrated APIs and collaborated with team members while maintaining a professional and positive work attitude. Demonstrated strong technical expertise.");
        profile.setSkillsTitle("My Technical Skills");
        profile.setSkillsDescription("A comprehensive set of technical skills spanning frontend, backend, and database technologies.");
        profile.setFrontendSkills("HTML:90,CSS:85,JavaScript:80,React:75,Bootstrap:85");
        profile.setBackendSkills("Java:85,Spring Boot:80,REST APIs:80,Servlets:75,JDBC:75");
        profile.setToolsSkills("MySQL:80,Git:75,GitHub:80,VS Code:85,Eclipse:80");
        return profile;
    }
}
