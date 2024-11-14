package ecom.demoecom.repo;

import ecom.demoecom.entity.OrderEcommerce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEcommerceRepository extends JpaRepository<OrderEcommerce, Long> {
}
