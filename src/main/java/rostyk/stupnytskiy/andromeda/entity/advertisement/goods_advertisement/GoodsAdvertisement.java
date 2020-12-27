package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.cart.CartItem;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.AdvertisementEntity;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class GoodsAdvertisement extends Advertisement implements AdvertisementEntity {

    private Boolean onlySellerCountry;

    private Integer count;

    @OneToMany(mappedBy = "goodsAdvertisement")
    private List<GoodsOrderItem> goodsOrderItem;

    @ManyToOne
    private Subcategory subcategory;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToOne
    private GoodsSellerAccount seller;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "goodsAdvertisement")
    private List<GoodsCartItem> cartItems;

    @ManyToOne
    private Currency currency;

    @Override
    public <T extends AdvertisementResponse> AdvertisementResponse mapToResponse() {
        return new GoodsAdvertisementResponse(this);
    }


    @Override
    public String toString() {
        return "GoodsAdvertisement{" +
                "onlySellerCountry=" + onlySellerCountry +
                ", images=" + images +
                '}';
    }
}

