package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementRequest;

import java.util.List;

@Getter
@Setter
public class GoodsAdvertisementRequest extends AdvertisementRequest {

    private Boolean onlySellerCountry;

    private Long subcategoryId;
    private Long currencyId;

    private List<String> images;

    private List<PropertyRequest> properties;

}
