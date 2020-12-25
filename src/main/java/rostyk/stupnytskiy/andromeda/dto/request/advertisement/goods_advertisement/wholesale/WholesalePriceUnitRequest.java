package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WholesalePriceUnitRequest {

    private Integer min;
    private Integer max;
    private Double price;

    @Override
    public String toString() {
        return "WholesalePriceUnitRequest{" +
                "min=" + min +
                ", max=" + max +
                ", price=" + price +
                '}';
    }
}
