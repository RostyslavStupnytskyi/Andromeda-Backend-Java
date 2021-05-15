package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ParametersValuesPriceCountResponse {

    private Long id;
    private Double price;
    private Double priceWithDiscount;
    private Integer count;
    private boolean isExchanged;

    private Map<String, String> values = new HashMap<>();


    public ParametersValuesPriceCountResponse(ParametersValuesPriceCount valuesPriceCount) {
        this.id = valuesPriceCount.getId();
        this.price = valuesPriceCount.getPriceByCurrency("USD");
        this.count = valuesPriceCount.getCount();
        if (valuesPriceCount.hasDiscount()) {
            this.priceWithDiscount = valuesPriceCount.getCurrentDiscountOrReturnNull().getPriceWithDiscount(this.price);

        }
        valuesPriceCount.getValues().forEach((par, parVal) -> this.values.put(par.getTitle(), parVal.getTitle()));
    }

    public ParametersValuesPriceCountResponse(ParametersValuesPriceCount valuesPriceCount, Currency currency) {
        this.id = valuesPriceCount.getId();
        this.price = valuesPriceCount.getPriceByCurrency(currency);
        this.count = valuesPriceCount.getCount();
        if (valuesPriceCount.hasDiscount()) {
            this.priceWithDiscount = valuesPriceCount.getCurrentDiscountOrReturnNull().getPriceWithDiscount(this.price);
        } else {
            this.priceWithDiscount = this.price;
        }
        valuesPriceCount.getValues().forEach((par, parVal) -> this.values.put(par.getTitle(), parVal.getTitle()));
    }

}
