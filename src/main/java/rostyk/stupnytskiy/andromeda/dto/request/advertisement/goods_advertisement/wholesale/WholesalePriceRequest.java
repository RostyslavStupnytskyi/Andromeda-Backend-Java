package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WholesalePriceRequest {

    private List<WholesalePriceUnitRequest> priceUnits;
}
