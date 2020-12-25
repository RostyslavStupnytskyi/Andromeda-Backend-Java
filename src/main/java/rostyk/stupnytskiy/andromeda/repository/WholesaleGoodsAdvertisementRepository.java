package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;

@Repository
public interface WholesaleGoodsAdvertisementRepository extends JpaRepository<WholesaleGoodsAdvertisement, Long> {
}
