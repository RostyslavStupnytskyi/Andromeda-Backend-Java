package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.Category;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsAdvertisementRepository extends JpaRepository<GoodsAdvertisement, Long>, JpaSpecificationExecutor<GoodsAdvertisement> {
//    Page<GoodsAdvertisement> findAll(GoodsAdvertisementSpecification goodsAdvertisementSpecification, Pageable mapToPageable);

    Page<GoodsAdvertisement> findPageBySellerId(Long id, Pageable pageable);

    Optional<GoodsAdvertisement> findByIdAndSeller(Long id, GoodsSellerAccount seller);


    @Query("select distinct a from GoodsAdvertisement a join a.users u where u.id = :id")
    Page<GoodsAdvertisement> getAllAdvertisementByUserId(@Param("id") Long id, Pageable pageable);

    @Query("select distinct a from GoodsAdvertisement a join a.subcategory s where s.category.title = :category order by rand()")
    Page<GoodsAdvertisement> getAllBySubcategoryCategory(@Param("category")String category, Pageable pageable);
//    Page<GoodsAdvertisement> findR(Category category, Pageable pageable);

    Page<GoodsAdvertisement> findAllByUsers_Id(Long id, Pageable pageable);
}
