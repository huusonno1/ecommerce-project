package ecom.demoecom.service;

import ecom.demoecom.dto.OrderRequest;
import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.OrderEcommerce;

public interface OrderService {
    OrderEcommerce placeOrder(Cart cart);

    OrderEcommerce placeOrder(OrderRequest orderRequest);

    boolean cancelOrder(Long orderId);
}
