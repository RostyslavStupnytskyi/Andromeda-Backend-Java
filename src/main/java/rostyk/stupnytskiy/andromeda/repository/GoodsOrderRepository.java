package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

import java.util.Optional;

@Repository
public interface GoodsOrderRepository extends JpaRepository<GoodsOrder,Long> {

    Optional<GoodsOrder> findOneByIdAndSeller(Long id, GoodsSellerAccount seller);
    Optional<GoodsOrder> findOneByIdAndUser(Long id, UserAccount user);
}
