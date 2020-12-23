package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePriceUnit;

@Repository
public interface WholesalePriceUnitRepository extends JpaRepository<WholesalePriceUnit, Long> {

}
