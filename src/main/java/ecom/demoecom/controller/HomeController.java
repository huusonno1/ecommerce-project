package ecom.demoecom.controller;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private CartService cartService;
    @GetMapping("/")
    public String home() {
        return "index"; // Corresponds to index.html in resources/templates
    }
    @GetMapping("/home")
    public String showHomePage(HttpSession session,Model model) {
        // Add attributes for items, cart, and orders to the model
        // You would typically fetch these from the database and add to the model
        String username = (String) session.getAttribute("username");
        // Retrieve the accountId from the session as a Long
        Long accountId = (Long) session.getAttribute("accountId");
        if (username != null) {
            model.addAttribute("username", username);
            model.addAttribute("accountId", accountId);
        } else {
            model.addAttribute("username", "Guest");
            model.addAttribute("accountId", "1");
        }
        model.addAttribute("items", getItems());  // Example method to get items
        model.addAttribute("cart", getCart(accountId));    // Example method to get cart details
        model.addAttribute("orders", getOrders());// Example method to get orders
        return "home"; // Return the home page template
    }

    private Object getItems() {
        // Fetch items from the database or service
        return null;
    }

    private Cart getCart(Long accountId) {
        // Fetch cart details from the database or service
        Cart cart = cartService.getCartByAccountId(accountId);

        return cart;
    }

    private Object getOrders() {
        // Fetch orders from the database or service
        return null;
    }
}
