package ecom.demoecom.service;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.OrderEcommerce;

public interface OrderService {
    OrderEcommerce placeOrder(Cart cart);
}
