package ecom.demoecom.service;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Item;

public interface CartService {
    Cart addToCart(Cart cart, Item item);
    Cart getCartById(Long id);
}
