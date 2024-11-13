package ecom.demoecom.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Bank extends Payment {
    private Long paymentId;
    private String bankName;
    private String accountNumber;
    private String transactionId; // Mã giao dịch ngân hàng
    private double bankFee; // Phí ngân hàng

    // Getters and Setters
}
