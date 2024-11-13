package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Drone extends Shipment {
    private Long droneId;
    private double weightLimit;
    private double distanceLimit;

    // Getters and Setters
}
