package rostyk.stupnytskiy.andromeda.dto.response.cart;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class CartSellerPositionResponse {

    private List<GoodsCartItemResponse> items = new ArrayList<>();
    private Long sellerId;
    private String sellerName;
    private String sellerCountry;
    private CurrencyResponse currencyResponse;

    public CartSellerPositionResponse(List<GoodsCartItem> items, GoodsSellerAccount seller, Currency currency) {
        this.sellerId = seller.getId();
        this.sellerName = seller.getShopName();
        this.currencyResponse = new CurrencyResponse(currency);
        items.forEach((i) -> this.items.add(new GoodsCartItemResponse(i, currency)));
        this.items.sort(Comparator.comparing(GoodsCartItemResponse::getDate, Comparator.reverseOrder()));
        this.sellerCountry = seller.getCountry().getCountryCode();
    }
}
