package oldimplement.dao;

import oldimplement.entity.CustomerActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerActivityRepository extends JpaRepository<CustomerActivity, Long> {
}
