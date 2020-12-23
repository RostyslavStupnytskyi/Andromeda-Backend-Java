package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;

import java.util.List;

@Getter
@Setter
public class RetailGoodsAdvertisementRequest extends GoodsAdvertisementRequest {
    private List<RetailPriceRequest> price;
}
