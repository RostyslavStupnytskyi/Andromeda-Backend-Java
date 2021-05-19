package rostyk.stupnytskiy.andromeda.repository.order.goods_order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentDetails;
import rostyk.stupnytskiy.andromeda.entity.order.changes.GoodsOrderSellerChange;

@Repository
public interface GoodsOrderSellerChangeRepository extends JpaRepository<GoodsOrderSellerChange, Long> {

}
