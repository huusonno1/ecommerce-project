package ecom.demoecom.service.impl;

import ecom.demoecom.entity.Account;
import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Item;
import ecom.demoecom.entity.User;
import ecom.demoecom.repo.CartRepository;
import ecom.demoecom.repo.ItemRepository;
import ecom.demoecom.repo.UserRepository;
import ecom.demoecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

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

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long itemId) {
        // Fetch the Cart by cartId
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        // Fetch the Item by itemId
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

        // Add the Item to the Cart's items list
        cart.getItems().add(item);

        // Update the totalQuantity and totalPrice
        cart.setTotalQuantity(cart.getTotalQuantity() + 1);
        cart.setTotalPrice(cart.getTotalPrice() + item.getPrice()); // Assuming Item has a getPrice() method

        // Save the updated Cart
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByAccountId(Long accountId) {
        User user = userRepository.getUserByAccountId(accountId);
        if(user == null) {
            return null;
        }
        Cart cart = cartRepository.findByUserId(user.getId());
        if(cart == null){
            cart = new Cart();
            cart.setStatus("open");
            cart.setUser(user);
            cart.setTotalQuantity(0);
            cart.setTotalPrice(0);
            cart.setLastUpdate(LocalDate.now());
            cart = cartRepository.save(cart);
        }
        return cart;
    }
}
