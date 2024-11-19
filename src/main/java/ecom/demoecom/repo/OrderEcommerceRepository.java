package ecom.demoecom.repo;

import ecom.demoecom.entity.OrderEcommerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderEcommerceRepository extends JpaRepository<OrderEcommerce, Long> {
    @Query("SELECT o FROM OrderEcommerce o WHERE o.user.id = :userId AND o.cart.id = :cartId")
    OrderEcommerce findByUserAndCart(Long userId, Long cartId);
    @Query("SELECT o FROM OrderEcommerce o WHERE o.cart.id = :cartId")
    OrderEcommerce findByCartId(Long cartId);
    @Query("SELECT o FROM OrderEcommerce o WHERE o.user.id = :userId AND o.cart.id = :cartId")
    OrderEcommerce findByUserIdAndCartId(Long userId, Long cartId);
}
