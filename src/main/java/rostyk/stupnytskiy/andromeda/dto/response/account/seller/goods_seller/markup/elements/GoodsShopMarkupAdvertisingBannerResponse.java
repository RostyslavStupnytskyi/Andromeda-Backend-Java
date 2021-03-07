package rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.GoodsShopMarkupAdvertisingBanner;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodsShopMarkupAdvertisingBannerResponse {
    private Long id;
    private List<String> images;

    public GoodsShopMarkupAdvertisingBannerResponse(GoodsShopMarkupAdvertisingBanner banner) {
        this.id = banner.getId();
        this.images = banner.getImages();
    }
}
