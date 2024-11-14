package ecom.demoecom.controller;

import ecom.demoecom.dto.CartRequest;
import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Item;
import ecom.demoecom.repo.CartRepository;
import ecom.demoecom.service.CartService;
import ecom.demoecom.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemService itemService;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart/{cartId}")
    public String viewCart(@PathVariable Long cartId, Model model) {
        // Fetch cart details using CartService if needed
        Cart cart = cartService.getCartById(cartId);

        // Add attributes to the model
        model.addAttribute("cart", cart);

        return "cart/cartViews"; // Name of the Thymeleaf template
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {
        try {
            Cart cart = cartService.getCartByAccountId(cartRequest.getAccountId());
            if(cart == null) {
                return null;
            }
            cartService.addItemToCart(cart.getId(), cartRequest.getItemId());
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
