package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterValuesCurrencyPriceRequest {
    private Double price;
    private String currencyCode;
}
