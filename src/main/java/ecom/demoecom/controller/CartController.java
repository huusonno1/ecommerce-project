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

    @GetMapping("/cart/view")
    public String viewCart(@RequestParam Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        model.addAttribute("cart", cart);
        return "cart"; // HTML page to display cart details
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
