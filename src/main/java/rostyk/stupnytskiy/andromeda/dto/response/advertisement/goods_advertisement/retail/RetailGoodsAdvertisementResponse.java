package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.retail;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.retail.RetailGoodsAdvertisement;

@Getter
@Setter
public class RetailGoodsAdvertisementResponse extends GoodsAdvertisementResponse {

    private RetailPriceResponse price;

    public RetailGoodsAdvertisementResponse(RetailGoodsAdvertisement advertisement) {
        super(advertisement);
        this.price = new RetailPriceResponse(advertisement.getCurrentPrice());
    }
}
