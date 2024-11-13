package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Cash extends Payment {
    private Long paymentId;
    private String receivedBy; // Người nhận tiền mặt

    // Getters and Setters
}

