package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class ShipFast extends Shipment {
    private Long shipmentId;
    private double expressFee;
    private String expectedDelivery;

    // Getters and Setters
}

