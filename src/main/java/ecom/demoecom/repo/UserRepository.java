package ecom.demoecom.repo;

import ecom.demoecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface  UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.account.id = :accountId")
    User getUserByAccountId(@Param("accountId") Long accountId);

}
