package ecom.demoecom.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long cartId;
    private Long shipmentId;
    private String paymentMethod;
    private Date paymentDate;
    private String status;

}
