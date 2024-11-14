package ecom.demoecom.service.impl;

import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.OrderEcommerce;
import ecom.demoecom.entity.OrderEcommerce;
import ecom.demoecom.repo.OrderEcommerceRepository;
import ecom.demoecom.repo.OrderEcommerceRepository;
import ecom.demoecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderEcommerceRepository orderRepository;

    @Override
    public OrderEcommerce placeOrder(Cart cart) {
        OrderEcommerce order = new OrderEcommerce();
        order.setItems(cart.getItems());
        order.setTotalPrice(cart.getTotalPrice());
        order.setDate(LocalDate.now());
        order.setStatus("Placed");
        return orderRepository.save(order);
    }
}
