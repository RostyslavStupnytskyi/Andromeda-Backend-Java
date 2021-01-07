package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.specification.GoodsAdvertisementSpecification;

@Repository
public interface GoodsAdvertisementRepository extends JpaRepository<GoodsAdvertisement, Long>, JpaSpecificationExecutor<GoodsAdvertisement> {
//    Page<GoodsAdvertisement> findAll(GoodsAdvertisementSpecification goodsAdvertisementSpecification, Pageable mapToPageable);
}
