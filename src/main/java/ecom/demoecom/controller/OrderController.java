package ecom.demoecom.controller;

import ecom.demoecom.dto.OrderRequest;
import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.OrderEcommerce;
import ecom.demoecom.entity.User;
import ecom.demoecom.service.CartService;
import ecom.demoecom.service.OrderService;
import ecom.demoecom.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.Order;
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

    @Autowired
    private UserService userService;


    @PostMapping("/checkout")
    public String createOrder(HttpSession session, @RequestParam Long accountId, @RequestParam Long cartId, Model model) {
        // Lấy thông tin người dùng và giỏ hàng từ session hoặc database
        User user = userService.getUserByAccountId(accountId);
        Cart cart = cartService.getCartById(cartId);

        if (user == null || cart == null) {
            // Xử lý trường hợp không tìm thấy người dùng hoặc giỏ hàng
            model.addAttribute("errorMessage", "User or cart not found.");
            return "error"; // Trả về trang lỗi nếu cần
        }
        // Kiểm tra xem đơn hàng đã tồn tại hay chưa
        if (!orderService.orderExistsForCart(cartId)) {
            // Tạo đơn hàng mới
            orderService.addNewOrder(user, cart);
        }

        OrderEcommerce order = orderService.getOrderBy(user.getId(), cart.getId());

        // Thêm các thuộc tính cần thiết vào model để hiển thị trên trang checkout
        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("order", order);

        // Trả về trang checkout.html
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
