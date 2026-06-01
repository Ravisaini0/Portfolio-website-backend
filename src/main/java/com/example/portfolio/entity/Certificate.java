package com.example.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String filePath;

    private String title;

    private String issuer;

    private String duration;

    private String location;

    private String date;

    private String grade;

    private String registrationNo;

    private String certificateNo;

    private String type;

    @Column(length = 1000)
    private String description;

    private String imageUrl;
}
