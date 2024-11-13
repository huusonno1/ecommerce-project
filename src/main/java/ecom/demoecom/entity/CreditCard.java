package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class CreditCard extends Payment {
    private Long paymentId;
    private String cardNumber;
    private String cardHolderName;
    private LocalDate expiryDate;
    private String cvv; // Mã bảo mật thẻ

    // Getters and Setters
}
