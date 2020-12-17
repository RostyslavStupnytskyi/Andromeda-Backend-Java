package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
}
