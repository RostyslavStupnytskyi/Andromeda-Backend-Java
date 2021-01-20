package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsAdvertisementRepository extends JpaRepository<GoodsAdvertisement, Long>, JpaSpecificationExecutor<GoodsAdvertisement> {
//    Page<GoodsAdvertisement> findAll(GoodsAdvertisementSpecification goodsAdvertisementSpecification, Pageable mapToPageable);

    Page<GoodsAdvertisement> findPageBySellerId(Long id, Pageable pageable);

    Optional<GoodsAdvertisement> findByIdAndSeller(Long id, GoodsSellerAccount seller);
}
