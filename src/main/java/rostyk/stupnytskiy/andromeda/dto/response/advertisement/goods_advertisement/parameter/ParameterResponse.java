package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParameterValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ParameterResponse {

    private Long id;
    private String title;
    private Boolean priceDependence;

    private List<ParameterValueResponse> values;

    public ParameterResponse(Parameter parameter) {
        this.id = parameter.getId();
        this.title = parameter.getTitle();
        this.priceDependence = parameter.getPriceDependence();
        this.values = parameter.getValues().stream().map(ParameterValueResponse::new).collect(Collectors.toList());
    }
}
