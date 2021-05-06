package rostyk.stupnytskiy.andromeda.dto.response.cart;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;

import java.util.*;

@Getter
@Setter
public class CartResponse {
    private Integer allItems = 0;
//    private Double sum = 0.0;
    private List<CartSellerPositionResponse> positions = new ArrayList<>();

    public CartResponse(Cart cart) {
        this.allItems = cart.getCartItems().size();
        for (GoodsSellerAccount seller : getSellers(cart.getCartItems())) {
            List<GoodsCartItem> items = new ArrayList<>();
            for (GoodsCartItem cartItem : cart.getCartItems())
                if (cartItem.getGoodsAdvertisement().getSeller().getId().equals(seller.getId()))
                    items.add(cartItem);
            this.positions.add(new CartSellerPositionResponse(items, seller));
        }
        this.positions.sort(Comparator.comparing((p) -> p.getItems().get(0).getDate(), Comparator.reverseOrder()));
    }

    private Set<GoodsSellerAccount> getSellers(List<GoodsCartItem> items) {
        Set<GoodsSellerAccount> sellers = new HashSet<>();
        items.forEach((i) -> sellers.add(i.getGoodsAdvertisement().getSeller()));
        return sellers;
    }
}
