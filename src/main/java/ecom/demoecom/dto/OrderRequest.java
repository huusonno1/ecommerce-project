package ecom.demoecom.dto;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Item;
import ecom.demoecom.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {
    private User user;
    private Cart cart;
    private int totalAmount;
    private double totalPrice;
    private LocalDate date;
    private Long shipmentId;
    private Long paymentId;
    private String status;
    private String shippingAddress;
}
