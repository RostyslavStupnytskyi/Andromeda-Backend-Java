package rostyk.stupnytskiy.andromeda.repository.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;

@Repository
public interface UserDeliveryAddressRepository extends JpaRepository<UserDeliveryAddress, Long> {
}
