package ecom.demoecom.controller;

import ecom.demoecom.dto.OrderRequest;
import ecom.demoecom.entity.*;
import ecom.demoecom.repo.*;
import ecom.demoecom.service.*;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
    @Autowired
    private CashRepository cashRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private OrderEcommerceRepository orderEcommerceRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/manage-order")
    public String manageOrder(HttpSession session,Model model) {

        List<OrderEcommerce> orders = orderService.getListOrder();
        model.addAttribute("orders", orders);
        return "/admin/manageOrder";
    }

    @GetMapping("/manage-order/edit/{orderId}")
    public String getEditOrderId(@PathVariable Long orderId, Model model) {
        OrderEcommerce order = orderEcommerceRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));
        Payment payment = paymentRepository.findById(order.getPaymentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + order.getPaymentId()));
        Shipment shipment = shipmentRepository.findById(order.getShipmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + order.getShipmentId()));
        Cart cart = cartRepository.findById(order.getCart().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + order.getCart().getId()));

        model.addAttribute("order", order);
        model.addAttribute("cart", cart);
        model.addAttribute("payment", payment);
        model.addAttribute("shipment", shipment);

        return "/admin/editOrderId";
    }

    @PostMapping("/manage-order/update-order/{orderId}")
    public String updateOrderId(@PathVariable Long orderId,
                                Model model,
                                @ModelAttribute("order") OrderEcommerce updatedOrder
    ) {
        OrderEcommerce order = orderEcommerceRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));

        order.setDate(updatedOrder.getDate());
        order.setPaymentId(updatedOrder.getPaymentId());
        order.setShipmentId(updatedOrder.getShipmentId());
        order.setShippingAddress(updatedOrder.getShippingAddress());
        order.setStatus(updatedOrder.getStatus());
        order.setTotalAmount(updatedOrder.getTotalAmount());
        order.setTotalPrice(updatedOrder.getTotalPrice());


        // Lưu thay đổi
        orderEcommerceRepository.save(order);


        List<OrderEcommerce> orders = orderService.getListOrder();
        model.addAttribute("orders", orders);
        return "/admin/manageOrder";
    }


    @PostMapping("/checkout")
    public String createOrder(HttpSession session, @RequestParam Long accountId, @RequestParam Long cartId, Model model) {
        // Fetch the user and cart
        User user = userService.getUserByAccountId(accountId);
        Cart cart = cartService.getCartById(cartId);

        if (user == null || cart == null) {
            model.addAttribute("errorMessage", "User or cart not found.");
            return "error";
        }

        // Check if an existing order is already present for the user and cart
        OrderEcommerce order = orderService.findOrderByUserAndCart(user.getId(), cart.getId());

        // If no existing order is found, create a new one
        if (order == null) {
            order = new OrderEcommerce();
            order.setUser(user);
            order.setCart(cart); // Optionally, save the new order
        }


        order.setStatus("pending");
        order.setShippingAddress(user.getAddress().getHomeAddress());
        order.setTotalAmount(cart.getTotalQuantity());
        order.setTotalPrice(cart.getTotalPrice());

        // Add the order to the model
        model.addAttribute("order", order);
        return "checkout";  // Return the checkout page
    }


    @PostMapping("/orders/checkout")
    public String saveOrder(@ModelAttribute("order") OrderEcommerce order,
                            @RequestParam String paymentType,
                            @RequestParam String shipmentType,
                            @RequestParam(required = false) String bankName,
                            @RequestParam(required = false) String accountNumber,
                            @RequestParam(required = false) String cardNumber,
                            @RequestParam(required = false) String cardHolderName,
                            @RequestParam(required = false) String expiryDate,
                            @RequestParam(required = false) String cvv) {
        // Set additional fields if necessary
        if (order.getDate() == null) {
            order.setDate(LocalDate.now()); // Set the current date if not already set
        }
        if (order.getStatus() == null) {
            order.setStatus("Pending"); // Set a default status if not already set
        }

        // Create the appropriate Payment subclass based on the selected payment type
        Payment payment;
        switch (paymentType) {
            case "cash":
                payment = new Cash();
                break;
            case "bank":
                Bank bank = new Bank();
                bank.setBankName(bankName);
                bank.setAccountNumber(accountNumber);
                payment = bank;
                break;
            case "creditCard":
                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(cardNumber);
                creditCard.setCardHolderName(cardHolderName);
                creditCard.setExpiryDate(LocalDate.now());
                creditCard.setCvv(cvv);
                payment = creditCard;
                break;
            default:
                throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        }
        paymentService.savePayment(payment);

        // Create the appropriate Shipment subclass based on the selected shipment type
        Shipment shipment;
        switch (shipmentType) {
            case "shipRegular":
                shipment = new ShipRegular();
                break;
            case "shipFast":
                shipment = new ShipFast();
                break;
            case "drone":
                shipment = new Drone();
                break;
            default:
                throw new IllegalArgumentException("Invalid shipment type: " + shipmentType);
        }
        shipmentService.saveShipment(shipment);

        order.setPaymentId(payment.getId());
        order.setShipmentId(shipment.getId());
        // Save the order
        orderService.saveOrder(order);

        Cart currentCart = order.getCart();
        currentCart.setStatus("Checked Out"); // Update the status
        cartService.saveCart(currentCart); // Save the updated cart

        // Create a new, empty cart for the user
        Cart newCart = new Cart();
        newCart.setUser(order.getUser());// Associate the new cart with the same user
        newCart.setStatus("pending");
        newCart.setLastUpdate(LocalDate.now());
        cartService.saveNewCart(newCart); // Save the new cart

        return "redirect:/home";
    }


    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        // Extract details from OrderRequest and place the order
        OrderEcommerce order = orderService.placeOrder(orderRequest);

        return ResponseEntity.ok("Order has been placed successfully with ID: " + order.getId());
    }

    @PutMapping("/order/place")
    public ResponseEntity<String> updatePlaceOrder(@RequestBody OrderRequest orderRequest) {
        // Extract details from OrderRequest and place the order
        OrderEcommerce order = orderService.updateOrder(orderRequest);

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
