package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.advertisement.WholesalePrice;

@Repository
public interface SellerStatisticsRepository extends JpaRepository<WholesalePrice, Long> {

}