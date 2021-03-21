package oldimplement.dao;

import oldimplement.entity.PriceTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceTrackingRepository extends JpaRepository<PriceTracking, Long> {
}
