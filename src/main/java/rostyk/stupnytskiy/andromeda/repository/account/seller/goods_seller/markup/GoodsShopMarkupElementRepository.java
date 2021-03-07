package rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;

@Repository
public interface GoodsShopMarkupElementRepository extends JpaRepository<GoodsShopMarkupElement, Long> {

}
