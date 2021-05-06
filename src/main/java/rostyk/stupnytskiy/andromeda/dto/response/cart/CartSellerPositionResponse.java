package rostyk.stupnytskiy.andromeda.dto.response.cart;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartSellerPositionResponse {

    private List<GoodsCartItemResponse> items = new ArrayList<>();
    private Long sellerId;
    private String sellerName;
    private String sellerCountry;

    public CartSellerPositionResponse(GoodsCartItemResponse item, GoodsSellerAccount seller) {
        this.items.add(item);
        this.sellerId = seller.getId();
        this.sellerName = seller.getShopName();
        this.sellerCountry = seller.getCountry().getCountryCode();
        this.items.sort(Comparator.comparing(GoodsCartItemResponse::getDate, Comparator.reverseOrder()));
    }

    public CartSellerPositionResponse(List<GoodsCartItem> items, GoodsSellerAccount seller) {
        items.forEach((i) -> this.items.add(new GoodsCartItemResponse(i)));
        this.sellerId = seller.getId();
        this.sellerName = seller.getShopName();
        this.items.sort(Comparator.comparing(GoodsCartItemResponse::getDate, Comparator.reverseOrder()));
        this.sellerCountry = seller.getCountry().getCountryCode();
    }
}
