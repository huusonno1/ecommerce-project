package ecom.demoecom.dto;

import ecom.demoecom.entity.Account;
import ecom.demoecom.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class RegistrationForm {
    private Account account;
    private User user;

    // Getters and Setters
}
