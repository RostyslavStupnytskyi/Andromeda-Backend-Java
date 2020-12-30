package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import java.util.Optional;

@Repository
public interface GoodsOrderItemRepository extends JpaRepository<GoodsOrderItem, Long> {
    Optional<GoodsOrderItem> findByIdAndGoodsOrderUser(Long id, UserAccount user);
}
