package rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories.GoodsSellerAdvertisementCategory;

import java.util.List;

@Repository
public interface GoodsSellerAdvertisementCategoryRepository extends JpaRepository<GoodsSellerAdvertisementCategory, Long> {

    List<GoodsSellerAdvertisementCategory> findAllByGoodsSeller(GoodsSellerAccount goodsSellerAccount);
}
