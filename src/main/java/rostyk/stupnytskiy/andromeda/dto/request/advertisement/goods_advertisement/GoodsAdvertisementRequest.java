package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.AdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter.ParameterRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountRequest;

import java.util.List;

@Getter
@Setter
public class GoodsAdvertisementRequest extends AdvertisementRequest {

    private Boolean onlySellerCountry;

    private Long subcategoryId;

    private List<String> images;

    private List<PropertyRequest> properties;

    private List<ParameterRequest> parameters;

    private Boolean hasParameters;

    private List<ParametersValuesPriceCountRequest> valuesPriceCounts;

    private List<Long> deliveryTypes;
}
