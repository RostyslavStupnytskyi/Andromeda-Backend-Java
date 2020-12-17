package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.WholesalePrice;

@Repository
public interface WholesalePriceRepository extends JpaRepository<WholesalePrice, Long> {

}
