package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.wholesale;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;

@Getter
@Setter
public class WholesaleGoodsAdvertisementResponse extends GoodsAdvertisementResponse {

    private WholesalePriceResponse price;


    public WholesaleGoodsAdvertisementResponse(WholesaleGoodsAdvertisement advertisement) {
        super(advertisement);
        this.price = new WholesalePriceResponse(advertisement.getCurrentPrice());
    }
}
