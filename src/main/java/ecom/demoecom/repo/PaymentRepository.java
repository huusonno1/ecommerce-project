package ecom.demoecom.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ecom.demoecom.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}