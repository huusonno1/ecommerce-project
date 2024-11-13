package ecom.demoecom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Account account;
    @Embedded
    private FullName fullName;
    @OneToOne
    private Address address;
    private String gender;
    private LocalDate dob;
    private String phoneNumber;
    private String email;

    // Getters and setters
}