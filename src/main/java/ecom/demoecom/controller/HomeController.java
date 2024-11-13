package ecom.demoecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // Corresponds to index.html in resources/templates
    }
    @GetMapping("/home")
    public String showHomePage(Model model) {
        // Add attributes for items, cart, and orders to the model
        // You would typically fetch these from the database and add to the model
        model.addAttribute("items", getItems());  // Example method to get items
        model.addAttribute("cart", getCart());    // Example method to get cart details
        model.addAttribute("orders", getOrders());// Example method to get orders
        return "home"; // Return the home page template
    }

    private Object getItems() {
        // Fetch items from the database or service
        return null;
    }

    private Object getCart() {
        // Fetch cart details from the database or service
        return null;
    }

    private Object getOrders() {
        // Fetch orders from the database or service
        return null;
    }
}
