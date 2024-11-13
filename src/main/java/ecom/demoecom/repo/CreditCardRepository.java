package ecom.demoecom.repo;

import ecom.demoecom.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
