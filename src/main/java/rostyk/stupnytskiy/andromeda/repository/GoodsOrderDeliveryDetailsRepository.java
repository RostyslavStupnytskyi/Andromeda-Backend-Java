package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderDeliveryDetails;

@Repository
public interface GoodsOrderDeliveryDetailsRepository extends JpaRepository<GoodsOrderDeliveryDetails,Long> {
}
