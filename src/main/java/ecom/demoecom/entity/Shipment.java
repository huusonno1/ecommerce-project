package ecom.demoecom.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String typeShip;  // Ví dụ: "ShipFast", "ShipRegular", "Drone"
    private String status;
    private String estimatedDelivery;
    private String attribute; // Thông tin bổ sung hoặc tùy chỉnh


    // Getters and Setters
}
