package ecom.demoecom.service.impl;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.Order;
import ecom.demoecom.repo.OrderRepository;
import ecom.demoecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order placeOrder(Cart cart) {
        Order order = new Order();
        order.setItems(cart.getItems());
        order.setTotalPrice(cart.getTotalPrice());
        order.setDate(LocalDate.now());
        order.setStatus("Placed");
        return orderRepository.save(order);
    }
}
