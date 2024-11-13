package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class ShipRegular extends Shipment {
    private Long shipmentId;
    private double standardFee;
    private String expectedDelivery;

    // Getters and Setters
}
