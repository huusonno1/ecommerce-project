package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Shoes extends Item {
    private String shoesType;
    private String shoesProducer;
    private String shoesSize;

    // Getters and Setters
}
