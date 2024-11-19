package ecom.demoecom.dto;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Item;
import ecom.demoecom.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {
    private User user;
    private Cart cart;
    private List<Item> items;
    private int totalAmount;
    private double totalPrice;
    private LocalDate date;
    private Long shipmentId;
    private Long paymentId;
    private String status;
    private String shippingAddress;
}
