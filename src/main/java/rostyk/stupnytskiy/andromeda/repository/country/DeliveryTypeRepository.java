package rostyk.stupnytskiy.andromeda.repository.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;

import java.util.List;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {
    List<DeliveryType> getAllByCountryCountryCode(String code);

    @Query("select distinct d from DeliveryType d join d.goodsAdvertisements a where a.id = :id")
    List<DeliveryType> getAllByAdvertisementId(@Param("id") Long id);
}
