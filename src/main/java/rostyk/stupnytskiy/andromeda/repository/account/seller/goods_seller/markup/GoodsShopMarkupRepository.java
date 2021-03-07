package rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;

@Repository
public interface GoodsShopMarkupRepository extends JpaRepository<GoodsShopMarkup, Long> {

}
