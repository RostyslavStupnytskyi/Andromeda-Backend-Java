package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

@Repository
public interface GoodsOrderRepository extends JpaRepository<GoodsOrder,Long> {
}
