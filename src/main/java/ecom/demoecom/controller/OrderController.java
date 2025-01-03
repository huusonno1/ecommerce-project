package ecom.demoecom.controller;

import ecom.demoecom.dto.OrderRequest;
import ecom.demoecom.entity.*;
import ecom.demoecom.repo.*;
import ecom.demoecom.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    private ShippingAddressRepository shippingAddressRepository;
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
            orderService.saveOrder(order);
        }


        order.setStatus("pending");
        order.setTotalAmount(cart.getTotalQuantity());
        order.setTotalPrice(cart.getTotalPrice());

        // Add the order to the model
        model.addAttribute("order", order);
        return "checkout";  // Return the checkout page
    }


    @PostMapping("/orders/checkout")
    public String saveOrder(
            @ModelAttribute("order") OrderEcommerce order,
            @RequestParam String paymentType,
            @RequestParam String shipmentType,
            @RequestParam(required = false) String bankName,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String cardNumber,
            @RequestParam(required = false) String cardHolderName,
            @RequestParam(required = false) String expiryDate,
            @RequestParam(required = false) String cvv,
            @RequestParam(required = false) String homeAddress,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String ward,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district) {

        // Set default values for order
        setDefaultOrderValues(order);

        // Create and save shipping address
        ShippingAddress shippingAddress = createShippingAddress(order.getId(), homeAddress, country, ward, city, district);
        shippingAddressRepository.save(shippingAddress);

        // Create and save payment
        Payment payment = createPayment(paymentType, bankName, accountNumber, cardNumber, cardHolderName, expiryDate, cvv);
        paymentService.savePayment(payment);

        // Create and save shipment
        Shipment shipment = createShipment(shipmentType);
        shipmentService.saveShipment(shipment);

        // Associate payment and shipment with order
        order.setPaymentId(payment.getId());
        order.setShipmentId(shipment.getId());

        order.setShippingAddress(shippingAddress.getHomeAddress());
        order.setShippingAddressId(shippingAddress.getId());
        // Save the order
        orderService.saveOrder(order);

        // Update the current cart and create a new cart for the user
        handleCartAfterCheckout(order);

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

    @GetMapping("/{accountId}/orders")
    public String viewOrderHistory(@PathVariable Long accountId, Model model) {
        // Load orders for the given accountId
        List<OrderEcommerce> orders = orderService.getOrdersByAccountId(accountId);

        // Add orders to the model for Thymeleaf
        model.addAttribute("orders", orders);

        // Return the Thymeleaf view name
        return "historyorder";
    }
    // Helper Methods
    private void setDefaultOrderValues(OrderEcommerce order) {
        if (order.getDate() == null) {
            order.setDate(LocalDate.now());
        }
        if (order.getStatus() == null) {
            order.setStatus("Pending");
        }
    }

    private ShippingAddress createShippingAddress(Long orderId, String homeAddress, String country, String ward, String city, String district) {
        ShippingAddress address = new ShippingAddress();
        address.setOrderId(orderId);
        address.setHomeAddress(homeAddress);
        address.setCountry(country);
        address.setWard(ward);
        address.setCity(city);
        address.setDistrict(district);
        return address;
    }

    private Payment createPayment(String paymentType, String bankName, String accountNumber, String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        switch (paymentType) {
            case "cash":
                return new Cash();
            case "bank":
                Bank bank = new Bank();
                bank.setBankName(bankName);
                bank.setAccountNumber(accountNumber);
                return bank;
            case "creditCard":
                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(cardNumber);
                creditCard.setCardHolderName(cardHolderName);
                creditCard.setCvv(cvv);
                return creditCard;
            default:
                throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        }
    }

    private Shipment createShipment(String shipmentType) {
        switch (shipmentType) {
            case "shipRegular":
                return new ShipRegular();
            case "shipFast":
                return new ShipFast();
            case "drone":
                return new Drone();
            default:
                throw new IllegalArgumentException("Invalid shipment type: " + shipmentType);
        }
    }

    private void handleCartAfterCheckout(OrderEcommerce order) {
        Cart currentCart = order.getCart();
        currentCart.setStatus("Checked Out");
        cartService.saveCart(currentCart);

        Cart newCart = new Cart();
        newCart.setUser(order.getUser());
        newCart.setStatus("Pending");
        newCart.setLastUpdate(LocalDate.now());
        cartService.saveNewCart(newCart);
    }
}
