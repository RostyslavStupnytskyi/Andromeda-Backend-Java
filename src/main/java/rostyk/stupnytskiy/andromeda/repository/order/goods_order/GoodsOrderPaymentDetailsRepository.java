package rostyk.stupnytskiy.andromeda.repository.order.goods_order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentDetails;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GoodsOrderPaymentDetailsRepository extends JpaRepository<GoodsOrderPaymentDetails, Long> {


}
