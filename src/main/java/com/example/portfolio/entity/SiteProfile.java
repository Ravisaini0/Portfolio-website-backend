package com.example.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteProfile {

    @Id
    private Long id = 1L;

    private String fullName;

    private String role;

    @Column(length = 2000)
    private String bio;

    private String email;

    private String phone;

    private String location;

    private String githubUrl;

    private String linkedinUrl;

    private String profileImageUrl;

    private String aboutTitle;

    @Column(length = 2000)
    private String aboutDescription;

    private String highlightExperience;

    private String highlightSkills;

    private String highlightEducation;

    private String highlightTeamwork;

    private String workTitle;

    private String workCompanyPeriod;

    @Column(length = 1000)
    private String workDescription;

    private String skillsTitle;

    @Column(length = 1000)
    private String skillsDescription;

    @Column(length = 1000)
    private String frontendSkills;

    @Column(length = 1000)
    private String backendSkills;

    @Column(length = 1000)
    private String toolsSkills;
}
