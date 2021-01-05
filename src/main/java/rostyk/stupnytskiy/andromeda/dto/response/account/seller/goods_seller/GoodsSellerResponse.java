package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.SellerResponse;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GoodsSellerResponse extends SellerResponse {

    private Boolean onlySellerCountryDelivery = false;

    public GoodsSellerResponse(SellerAccount sellerAccount) {
        super(sellerAccount);
    }
}
