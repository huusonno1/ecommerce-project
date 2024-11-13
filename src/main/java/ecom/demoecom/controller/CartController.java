package ecom.demoecom.controller;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Item;
import ecom.demoecom.service.CartService;
import ecom.demoecom.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/cart/view")
    public String viewCart(@RequestParam Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        model.addAttribute("cart", cart);
        return "cart"; // HTML page to display cart details
    }

    @PostMapping("/cart/add/{itemId}")
    public String addToCart(@PathVariable Long itemId, @RequestParam Long cartId) {
        Item item = itemService.getItemById(itemId);
        Cart cart = cartService.getCartById(cartId);
        cartService.addToCart(cart, item);
        return "redirect:/cart/view?cartId=" + cartId; // Redirect to view cart
    }
}
