package ecom.demoecom.repo;

import ecom.demoecom.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {
}
