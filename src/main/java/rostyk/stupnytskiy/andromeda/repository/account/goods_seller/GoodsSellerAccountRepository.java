package rostyk.stupnytskiy.andromeda.repository.account.goods_seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;

@Repository
public interface GoodsSellerAccountRepository extends JpaRepository<GoodsSellerAccount, Long> {

}
