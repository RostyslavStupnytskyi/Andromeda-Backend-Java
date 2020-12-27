package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

@Repository
public interface GoodsOrderItemRepository extends JpaRepository<GoodsOrderItem, Long> {
}
