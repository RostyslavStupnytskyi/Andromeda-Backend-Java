package rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_view.GoodsShopMarkupAdvertisingViewType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;

@Getter
@Setter
public class GoodsShopMarkupAdvertisementViewRequest {

    private Long goodsAdvertisementId;
    private GoodsShopMarkupAdvertisingViewType viewType;
    private Integer line;
    private Integer position;
}
