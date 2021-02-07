package rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParameterRequest {

    private String title;

    private Boolean priceDependence;

    private List<ParameterValueRequest> values;
}
