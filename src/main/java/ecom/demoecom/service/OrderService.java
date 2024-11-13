package ecom.demoecom.service;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Order;

public interface OrderService {
    Order placeOrder(Cart cart);
}
