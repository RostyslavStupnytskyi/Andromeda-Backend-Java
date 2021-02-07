package rostyk.stupnytskiy.andromeda.repository.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<UserDeliveryAddress, Long> {

    List<UserDeliveryAddress> findAllByUser(UserAccount user);

    Optional<UserDeliveryAddress> findByUserAndId(UserAccount user, Long id);
}
