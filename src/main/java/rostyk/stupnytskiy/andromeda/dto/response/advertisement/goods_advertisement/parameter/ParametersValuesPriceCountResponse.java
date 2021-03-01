package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ParametersValuesPriceCountResponse {

    private Long id;
    private Double price;
    private Double priceWithDiscount;
    private Integer count;

    private Map<String, String> values = new HashMap<>();


    public ParametersValuesPriceCountResponse(ParametersValuesPriceCount valuesPriceCount) {
        this.id = valuesPriceCount.getId();
        this.price = valuesPriceCount.getPrice();
        this.count = valuesPriceCount.getCount();
        if (valuesPriceCount.hasDiscount()) {
            this.priceWithDiscount = valuesPriceCount.getCurrentDiscount().getPriceWithDiscount(this.price);

        }
        valuesPriceCount.getValues().forEach((par, parVal) -> this.values.put(par.getTitle(), parVal.getTitle()));
    }

}
