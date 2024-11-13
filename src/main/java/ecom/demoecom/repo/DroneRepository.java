package ecom.demoecom.repo;

import ecom.demoecom.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
