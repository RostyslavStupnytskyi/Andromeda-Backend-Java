package rostyk.stupnytskiy.andromeda.dto.response.advertisement;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePriceUnit;


@Getter
@Setter
public class WholesalePriceUnitResponse {

    private Long id;

    private Integer min;

    private Integer max;

    private Double price;

    public WholesalePriceUnitResponse(WholesalePriceUnit wholesalePriceUnit) {
        this.id = wholesalePriceUnit.getId();
        this.min = wholesalePriceUnit.getMin();
        this.max = wholesalePriceUnit.getMax();
        this.price = wholesalePriceUnit.getPrice();
    }
}
