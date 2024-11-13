package ecom.demoecom.controller;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Order;
import ecom.demoecom.service.CartService;
import ecom.demoecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/order/place")
    public String placeOrder(@RequestParam Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        Order order = orderService.placeOrder(cart);
        model.addAttribute("order", order);
        return "order-confirmation"; // HTML page to display order confirmation
    }
}
