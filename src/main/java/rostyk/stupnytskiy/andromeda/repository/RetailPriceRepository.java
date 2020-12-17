package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;

@Repository
public interface RetailPriceRepository extends JpaRepository<RetailPrice, Long> {

}
