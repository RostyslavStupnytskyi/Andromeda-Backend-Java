package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_view.GoodsShopMarkupAdvertisementView;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_view.GoodsShopMarkupAdvertisingViewType;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
public class GoodsShopMarkupAdvertisementViewResponse {
    private Long id;

    private GoodsAdvertisementResponse goodsAdvertisement;

    private GoodsShopMarkupAdvertisingViewType viewType;

    public GoodsShopMarkupAdvertisementViewResponse(GoodsShopMarkupAdvertisementView view, GoodsAdvertisement goodsAdvertisement) {
        this.id = view.getId();
        this.viewType = view.getViewType();
        this.goodsAdvertisement = new GoodsAdvertisementResponse(goodsAdvertisement);
    }
}
