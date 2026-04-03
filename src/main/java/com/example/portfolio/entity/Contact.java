package com.example.portfolio.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
	@NotBlank(message = "Name is required")
private String name;
	@Email(message = "Invalid email")
private String email;
	@NotBlank(message = "Phone is required")
private String phone;
private String company;
	private String subject;
	@NotBlank(message = "Message is required")
private String message;
}
