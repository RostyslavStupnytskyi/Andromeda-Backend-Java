package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.wholesale;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;

@Getter
@Setter
public class WholesaleGoodsAdvertisementForSearchResponse extends GoodsAdvertisementForSearchResponse {

    private String price;

    public WholesaleGoodsAdvertisementForSearchResponse(WholesaleGoodsAdvertisement advertisement){
        super(advertisement);
        this.price = advertisement.getCurrentPrice().getStringPriceSides();
    }
}
