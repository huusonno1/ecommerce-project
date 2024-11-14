package ecom.demoecom.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long accountId;
    private Long itemId;
}
