package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WholesalePriceUnitRequest {

    private Integer min;
    private Integer max;
    private Double price;

}
