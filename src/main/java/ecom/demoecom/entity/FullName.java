package ecom.demoecom.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

// FullName.java
@Embeddable
@Data
public class FullName {
    private String firstName;
    private String midName;
    private String lastName;

    // Getters and setters
}