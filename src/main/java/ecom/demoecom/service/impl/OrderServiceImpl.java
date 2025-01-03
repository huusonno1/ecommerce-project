package ecom.demoecom.service.impl;

import ecom.demoecom.dto.OrderRequest;
import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.OrderEcommerce;
import ecom.demoecom.entity.User;
import ecom.demoecom.repo.OrderEcommerceRepository;
import ecom.demoecom.repo.UserRepository;
import ecom.demoecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderEcommerceRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderEcommerce> getListOrder() {
        List<OrderEcommerce> orders = orderRepository.findAll();
        return orders;
    }

    @Override
    public OrderEcommerce placeOrder(Cart cart) {
        OrderEcommerce order = new OrderEcommerce();
        order.setTotalPrice(cart.getTotalPrice());
        order.setDate(LocalDate.now());
        order.setStatus("Placed");
        return orderRepository.save(order);
    }

    @Override
    public OrderEcommerce placeOrder(OrderRequest orderRequest) {
        OrderEcommerce order = new OrderEcommerce();
        order.setUser(orderRequest.getUser());
        order.setCart(orderRequest.getCart());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setDate(LocalDate.now());
        order.setShipmentId(orderRequest.getShipmentId());
        order.setPaymentId(orderRequest.getPaymentId());
        order.setStatus(orderRequest.getStatus());
        order.setShippingAddress(orderRequest.getShippingAddress());
        return orderRepository.save(order);

    }

    @Override
    public boolean cancelOrder(Long orderId) {
        return false;
    }

    @Override
    public void addNewOrder(User user, Cart cart) {
        OrderEcommerce order = new OrderEcommerce();
        order.setUser(user);
        order.setCart(cart);
        order.setDate(LocalDate.now());
        order.setStatus("pending");
        order.setTotalAmount(cart.getTotalQuantity());
        order.setTotalPrice(cart.getTotalPrice());
        order.setShippingAddress(user.getAddress().getHomeAddress());

        orderRepository.save(order);
    }

    @Override
    public boolean orderExistsForCart(Long cartId) {
        OrderEcommerce orderEcommerce = null;
        orderEcommerce = orderRepository.findByCartId(cartId);
        if(orderEcommerce != null){
            return true;
        }
        return false;
    }

    @Override
    public OrderEcommerce getOrderBy(Long userId, Long cartId) {
        return orderRepository.findByUserAndCart(userId, cartId);
    }

    @Override
    public OrderEcommerce updateOrder(OrderRequest orderRequest) {
        OrderEcommerce order = new OrderEcommerce();
        order.setUser(orderRequest.getUser());
        order.setCart(orderRequest.getCart());
        order.setDate(LocalDate.now());
        order.setStatus(orderRequest.getStatus());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setShippingAddress(orderRequest.getUser().getAddress().getHomeAddress());
        order.setPaymentId(1L);
        order.setShipmentId(1L);
        orderRepository.save(order);
        return order;
    }

    @Override
    public void saveOrder(OrderEcommerce order) {
        orderRepository.save(order);
    }

    @Override
    public OrderEcommerce findOrderByUserAndCart(Long userId, Long cartId) {
        return orderRepository.findByUserIdAndCartId(userId, cartId);
    }

    @Override
    public List<OrderEcommerce> getOrdersByAccountId(Long accountId) {
        User user = userRepository.getUserByAccountId(accountId);
        return orderRepository.findAllByUserId(user.getId());
    }
}