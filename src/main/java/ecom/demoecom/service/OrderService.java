package ecom.demoecom.service;

import ecom.demoecom.dto.OrderRequest;
import ecom.demoecom.entity.Cart;
import ecom.demoecom.entity.OrderEcommerce;
import ecom.demoecom.entity.User;
import org.hibernate.query.Order;

import java.util.List;

public interface OrderService {
    List<OrderEcommerce> getListOrder();

    OrderEcommerce placeOrder(Cart cart);

    OrderEcommerce placeOrder(OrderRequest orderRequest);

    boolean cancelOrder(Long orderId);

    void addNewOrder(User user, Cart cart);

    boolean orderExistsForCart(Long cartId);

    OrderEcommerce getOrderBy(Long id, Long id1);

    OrderEcommerce updateOrder(OrderRequest orderRequest);

    void saveOrder(OrderEcommerce order);

    OrderEcommerce findOrderByUserAndCart(Long id, Long id1);
}
