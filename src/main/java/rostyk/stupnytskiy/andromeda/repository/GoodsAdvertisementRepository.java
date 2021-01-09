package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import java.util.List;

@Repository
public interface GoodsAdvertisementRepository extends JpaRepository<GoodsAdvertisement, Long>, JpaSpecificationExecutor<GoodsAdvertisement> {
//    Page<GoodsAdvertisement> findAll(GoodsAdvertisementSpecification goodsAdvertisementSpecification, Pageable mapToPageable);
}
