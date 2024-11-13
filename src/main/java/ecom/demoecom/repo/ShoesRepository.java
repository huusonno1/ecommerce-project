package ecom.demoecom.repo;

import ecom.demoecom.entity.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {
}
