package ecom.demoecom.service.impl;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Item;
import ecom.demoecom.repo.CartRepository;
import ecom.demoecom.repo.ItemRepository;
import ecom.demoecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Cart addToCart(Cart cart, Item item) {
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>()); // Initialize the list if it's null
        }

        // Save the item to ensure it's in the database
        Item savedItem = itemRepository.save(item);

        // Add the saved item to the cart
        cart.getItems().add(savedItem);
        cart.setTotalQuantity(cart.getTotalQuantity() + 1);
        cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());

        // Save the cart
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(new Cart());
    }
}
