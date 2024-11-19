package ecom.demoecom.controller;

import ecom.demoecom.dto.OrderRequest;
import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.OrderEcommerce;
import ecom.demoecom.service.CartService;
import ecom.demoecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String showOrderPage(Model model) {
        return "checkout";
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        // Extract details from OrderRequest and place the order
        OrderEcommerce order = orderService.placeOrder(orderRequest);

        return ResponseEntity.ok("Order has been placed successfully with ID: " + order.getId());
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        boolean isCanceled = orderService.cancelOrder(orderId);

        if (isCanceled) {
            return ResponseEntity.ok("Order has been canceled successfully!");
        } else {
            return ResponseEntity.status(404).body("Order not found.");
        }
    }
}
