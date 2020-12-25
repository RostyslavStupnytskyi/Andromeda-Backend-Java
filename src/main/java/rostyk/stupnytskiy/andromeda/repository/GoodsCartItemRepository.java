package rostyk.stupnytskiy.andromeda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.cart.CartItem;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;

import java.util.Optional;

@Repository
public interface GoodsCartItemRepository extends JpaRepository<GoodsCartItem, Long> {
    boolean existsByCartAndGoodsAdvertisementId(Cart cart, Long id);
    Optional<GoodsCartItem> findOneByIdAndCart(Long id, Cart cart);
}
