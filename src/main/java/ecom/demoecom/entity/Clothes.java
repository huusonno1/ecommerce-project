package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Clothes extends Item {
    private String clothesType;
    private String clothesProducer;
    private String clothesSize;

    // Getters and Setters
}
