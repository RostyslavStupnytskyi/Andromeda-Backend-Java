package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {

}
