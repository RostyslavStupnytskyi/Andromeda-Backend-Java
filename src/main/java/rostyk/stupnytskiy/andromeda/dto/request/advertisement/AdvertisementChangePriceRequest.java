package rostyk.stupnytskiy.andromeda.dto.request.advertisement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementChangePriceRequest {

    private RetailPriceRequest retailPriceRequest;

    private WholesalePriceRequest wholesalePriceRequest;
}
