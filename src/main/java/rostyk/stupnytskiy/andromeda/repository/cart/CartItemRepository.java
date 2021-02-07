package rostyk.stupnytskiy.andromeda.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.cart.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
