package ecom.demoecom.entity;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class OrderEcommerce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @OneToOne
    private Cart cart;
    @OneToMany
    private List<Item> items;
    @Column
    private int totalAmount;
    @Column
    private double totalPrice;
    private LocalDate date;
    private Long shipmentId;
    private Long paymentId;
    private String status;
    private String shippingAddress;

    // Getters and setters
}