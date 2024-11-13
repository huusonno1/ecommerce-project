package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Book extends Item {
    private String bookType;
    private String author;
    private String publisher;
}
