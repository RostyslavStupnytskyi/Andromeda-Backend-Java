package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale;

import lombok.Setter;
import lombok.Getter;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementRequest;

import java.util.List;

@Getter
@Setter
public class WholesaleGoodsAdvertisementRequest extends GoodsAdvertisementRequest {

    private List<WholesalePriceRequest> price;
}
