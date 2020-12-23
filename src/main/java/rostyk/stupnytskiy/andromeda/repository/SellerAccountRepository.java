package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;

@Repository
public interface SellerAccountRepository extends JpaRepository<SellerAccount, Long> {

}
