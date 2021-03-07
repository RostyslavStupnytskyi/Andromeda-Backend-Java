package rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;

import java.util.Optional;

@Repository
public interface GoodsShopMarkupLineRepository extends JpaRepository<GoodsShopMarkupLine, Long> {
    Optional<GoodsShopMarkupLine> findOneByMarkupAndOrder(GoodsShopMarkup markup, Integer order);
}
