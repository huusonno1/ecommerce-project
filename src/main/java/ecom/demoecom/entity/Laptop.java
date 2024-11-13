package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Laptop extends Item{
    private String laptopProducer;
    private String laptopType;
}
