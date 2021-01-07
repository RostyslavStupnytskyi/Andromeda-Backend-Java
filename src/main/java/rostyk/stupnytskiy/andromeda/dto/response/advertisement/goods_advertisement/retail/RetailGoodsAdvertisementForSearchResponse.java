package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.retail;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;

@Getter
@Setter
public class RetailGoodsAdvertisementForSearchResponse extends GoodsAdvertisementForSearchResponse {
    private String price;

    public RetailGoodsAdvertisementForSearchResponse(RetailGoodsAdvertisement advertisement) {
        super(advertisement);
        this.price = advertisement.getCurrentPrice().getStringPrice();
    }
}
