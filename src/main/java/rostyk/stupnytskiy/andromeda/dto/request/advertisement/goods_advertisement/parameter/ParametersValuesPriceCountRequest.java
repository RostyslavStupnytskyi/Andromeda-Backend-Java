package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ParametersValuesPriceCountRequest {

    private Map<String, String> valueParam;

    private List<ParameterValuesCurrencyPriceRequest> prices;

    private Integer count;
}
