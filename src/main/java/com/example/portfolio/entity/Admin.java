package com.example.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String username;
private String password;

}
