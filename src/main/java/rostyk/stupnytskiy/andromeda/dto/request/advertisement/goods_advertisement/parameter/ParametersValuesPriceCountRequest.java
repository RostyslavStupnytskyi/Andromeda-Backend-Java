package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class ParametersValuesPriceCountRequest {

    private Map<String, String> valueParam;

    private Double price;

    private Integer count;
}
