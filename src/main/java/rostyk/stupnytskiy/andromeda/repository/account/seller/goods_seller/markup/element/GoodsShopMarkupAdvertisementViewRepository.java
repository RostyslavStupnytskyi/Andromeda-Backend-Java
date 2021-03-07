package rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.element;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row.GoodsShopMarkupAdvertisementRow;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_view.GoodsShopMarkupAdvertisementView;

import java.util.Optional;

@Repository
public interface GoodsShopMarkupAdvertisementViewRepository extends JpaRepository<GoodsShopMarkupAdvertisementView, Long> {
    Optional<GoodsShopMarkupAdvertisementView> findOneByElementId(Long id);
}
