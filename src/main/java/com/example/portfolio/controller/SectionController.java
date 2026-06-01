package com.example.portfolio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.portfolio.entity.AboutItem;
import com.example.portfolio.entity.SkillItem;
import com.example.portfolio.entity.WorkExperience;
import com.example.portfolio.repository.AboutItemRepository;
import com.example.portfolio.repository.SkillItemRepository;
import com.example.portfolio.repository.WorkExperienceRepository;

@RestController
@RequestMapping("/api")
public class SectionController {

    private final AboutItemRepository aboutItemRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final SkillItemRepository skillItemRepository;

    public SectionController(
            AboutItemRepository aboutItemRepository,
            WorkExperienceRepository workExperienceRepository,
            SkillItemRepository skillItemRepository
    ) {
        this.aboutItemRepository = aboutItemRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.skillItemRepository = skillItemRepository;
    }

    @GetMapping("/about-items")
    public List<AboutItem> getAboutItems() {
        return aboutItemRepository.findAll();
    }

    @PostMapping("/about-items")
    public AboutItem saveAboutItem(@RequestBody AboutItem item) {
        return aboutItemRepository.save(item);
    }

    @DeleteMapping("/about-items/{id}")
    public void deleteAboutItem(@PathVariable Long id) {
        aboutItemRepository.deleteById(id);
    }

    @GetMapping("/experiences")
    public List<WorkExperience> getExperiences() {
        return workExperienceRepository.findAll();
    }

    @PostMapping("/experiences")
    public WorkExperience saveExperience(@RequestBody WorkExperience experience) {
        return workExperienceRepository.save(experience);
    }

    @DeleteMapping("/experiences/{id}")
    public void deleteExperience(@PathVariable Long id) {
        workExperienceRepository.deleteById(id);
    }

    @GetMapping("/skills")
    public List<SkillItem> getSkills() {
        return skillItemRepository.findAll();
    }

    @PostMapping("/skills")
    public SkillItem saveSkill(@RequestBody SkillItem skill) {
        return skillItemRepository.save(skill);
    }

    @DeleteMapping("/skills/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skillItemRepository.deleteById(id);
    }
}
